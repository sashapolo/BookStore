package db;

import business.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.GregorianCalendar;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 * Created with IntelliJ IDEA.
 * User: alexander
 * Date: 5/21/13
 * Time: 12:32 AM
 * To change this template use File | Settings | File Templates.
 */
public class OrderMapperTest {
    private static int orderId_;
    private static int bookId_;
    private static int userId_;
    private static int pubId_;
    public static final double EPSILON = 1e-15;

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @BeforeClass
    public static void setUpDatabase() throws Exception {
        final TestConnectionManager manager = new TestConnectionManager();
        Statement statement = null;
        try (Connection connection = manager.getConnection("testdb")) {
            String query = "INSERT into Publishers(Name) VALUES ('Mad Jack')";
            statement = connection.createStatement();
            statement.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);
            pubId_ = Mapper.getId(statement);

            query = "INSERT into Users(Type, Login, Password, Name, SecondName, Email, PersonalDiscount) " +
                    "VALUES (0, 'baz', 0, 'Elvis', 'Presley', 'Baby', 0)";
            statement = connection.createStatement();
            statement.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);
            userId_ = Mapper.getId(statement);

            query = "INSERT into Books(Name, Author, Genre, Isbn, PublicationDate, Price, Discount, PublisherId) " +
                    "VALUES ('foo', 'nya', 'bar', '9783161484100', '2012-01-01', 200, 10, " + pubId_ + ')';
            statement = connection.createStatement();
            statement.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);
            bookId_ = Mapper.getId(statement);

            query = "INSERT into Orders (CreationDate, Status, CustomerId) " +
                    "VALUES ('2012-01-01', 0, " + userId_ + ')';
            statement = connection.createStatement();
            statement.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);
            orderId_ = Mapper.getId(statement);

            query = "INSERT into OrderEntries(BookId, Amount) " +
                    "VALUES (" + bookId_ + ", 10)";
            statement = connection.createStatement();
            statement.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);
            final int entryId = Mapper.getId(statement);

            query = "INSERT into Cart " +
                    "VALUES (" + orderId_ + ", " + entryId + ')';
            statement = connection.createStatement();
            statement.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);
        } finally {
            if (statement != null) statement.close();
        }
    }

    @AfterClass
    public static void clearDatabase() throws SQLException {
        final TestConnectionManager manager = new TestConnectionManager();
        Statement statement = null;
        try (Connection connection = manager.getConnection("testdb")) {
            String query = "DELETE from Cart";
            statement = connection.createStatement();
            statement.executeUpdate(query);
            query = "DELETE from OrderEntries";
            statement = connection.createStatement();
            statement.executeUpdate(query);
            query = "DELETE from Orders";
            statement = connection.createStatement();
            statement.executeUpdate(query);
            query = "DELETE from Books";
            statement = connection.createStatement();
            statement.executeUpdate(query);
            query = "DELETE from Users";
            statement = connection.createStatement();
            statement.executeUpdate(query);
        } finally {
            if (statement != null) statement.close();
        }
    }

    @Test
    public void selectTest() throws Exception {
        final TestConnectionManager manager = new TestConnectionManager();
        try (Connection connection = manager.getConnection("testdb")) {
            final Mapper<Order> mapper = new OrderMapper(connection);
            final Order test = mapper.find(orderId_);
            assertNotNull("Order not found", test);
            assertEquals("Incorrect customer", "Elvis", test.getOrderer().getName());
            assertEquals("Incorrect status", Order.OrderStatus.CREATED, test.getStatus());
            assertEquals("Incorrect cart size", 1, test.getCart().size());
            assertEquals("Incorrect price", 200 * 10 * 0.9, test.getPrice(), EPSILON);
        }
    }

    @Test
    public void updateTest() throws Exception {
        exception.expect(DataMapperException.class);
        exception.expectMessage("Orders should never be updated!");

        final TestConnectionManager manager = new TestConnectionManager();
        try (Connection connection = manager.getConnection("testdb")) {
            final Mapper<Order> mapper = new OrderMapper(connection);
            final Order test = mapper.find(orderId_);
            mapper.update(test);
        }
    }

    @Test
    public void insertTest() throws Exception {
        final TestConnectionManager manager = new TestConnectionManager();
        try (Connection connection = manager.getConnection("testdb")) {
            final Mapper<Order> orderMapper = new OrderMapper(connection);
            final int id = orderMapper.insert(createOrder(connection, "9992158107"));
            Order test = orderMapper.find(id);
            assertNotNull("Order not found", test);
            assertEquals("Incorrect customer", "Elvis", test.getOrderer().getName());
            assertEquals("Incorrect cart size", 2, test.getCart().size());
            assertEquals("Incorrect price", 200 * 0.9 * 100 + 50 * 120.44, test.getPrice(), EPSILON);
        }
    }

    @Test
    public void deleteTest() throws Exception {
        final TestConnectionManager manager = new TestConnectionManager();
        Statement statement = null;
        try (Connection connection = manager.getConnection("testdb")) {
            String query = "INSERT into OrderEntries(BookId, Amount) " +
                           "VALUES (" + bookId_ + ", 15)";
            statement = connection.createStatement();
            statement.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);
            final int entryId = Mapper.getId(statement);

            final Mapper<Order> orderMapper = new OrderMapper(connection);
            final Mapper<OrderEntry> entryMapper = new OrderEntryMapper(connection);
            final int id = orderMapper.insert(createOrder(connection, "097522980X"));

            query = "INSERT into Cart " +
                    "VALUES (" + id + ", " + entryId + ')';
            statement = connection.createStatement();
            statement.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);


            orderMapper.delete(id);
            final Order test = orderMapper.find(id);
            assertNull("Order was not deleted", test);
            final OrderEntry entryTest = entryMapper.find(entryId);
            assertNull("Entry was not deleted", entryTest);
        } finally {
            if (statement != null) statement.close();
        }
    }

    private Order createOrder(final Connection connection, final String isbn10) throws DataMapperException, WrongFormatException {
        final Mapper<User> userMapper = new UserMapper(connection);
        final Mapper<Publisher> pubMapper = new PublisherMapper(connection);
        final Mapper<Book> bookMapper = new BookMapper(connection);

        User user = userMapper.find(userId_);
        Publisher pub = pubMapper.find(pubId_);
        Book book1 = bookMapper.find(bookId_);
        final Book book2 = new Book.Builder("test",
                                            "",
                                            "",
                                            pub,
                                            new GregorianCalendar(),
                                            new Isbn10(isbn10).toIsbn13(),
                                            120.44).build();
        book2.setId(bookMapper.insert(book2));

        OrderEntry entry1 = new OrderEntry(-1, book1, 100);
        OrderEntry entry2 = new OrderEntry(-1, book2, 50);
        Cart cart = new Cart();
        cart.put(entry1);
        cart.put(entry2);

        return new Order.Builder(-1, cart, (Customer)user).build();
    }
}
