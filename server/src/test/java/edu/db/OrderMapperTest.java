package edu.db;

import business.*;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.GregorianCalendar;

import static org.junit.Assert.*;

/**
 * Created with IntelliJ IDEA.
 * User: alexander
 * Date: 5/21/13
 * Time: 12:32 AM
 * To change this template use File | Settings | File Templates.
 */
public final class OrderMapperTest {
    private static int orderId = -1;
    private static int bookId = -1;
    private static int userId = -1;
    private static int pubId = -1;
    public static final double EPSILON = 1.0e-15;

    @BeforeClass
    public static void setUpDatabase() throws Exception {
        final TestConnectionManager manager = new TestConnectionManager();
        try (Connection connection = manager.getConnection("testdb")) {
            final String query1 = "INSERT into Publishers(Name) VALUES ('Mad Jack')";
            try (final Statement statement = connection.createStatement()) {
                statement.executeUpdate(query1, Statement.RETURN_GENERATED_KEYS);
                pubId = Mapper.getId(statement);
            }

            final String query2 = "INSERT into Users(Type, Login, Password, Name, SecondName, Email, PersonalDiscount) " +
                                  "VALUES (0, 'baz', 0, 'Elvis', 'Presley', 'Baby', 0)";
            try (final Statement statement = connection.createStatement()) {
                statement.executeUpdate(query2, Statement.RETURN_GENERATED_KEYS);
                userId = Mapper.getId(statement);
            }

            final String query3 = "INSERT into Books(Name, Author, Genre, Isbn, PublicationDate, Price, Discount, PublisherId) " +
                                  "VALUES ('foo', 'nya', 'bar', '9783161484100', '2012-01-01', 200, 10, ?)";
            try (final PreparedStatement statement = connection.prepareStatement(query3, Statement.RETURN_GENERATED_KEYS)) {
                statement.setInt(1, pubId);
                statement.executeUpdate();
                bookId = Mapper.getId(statement);
            }

            final String query4 = "INSERT into Orders (CreationDate, Status, CustomerId) " +
                                  "VALUES ('2012-01-01', 0, ?)";
            try (final PreparedStatement statement = connection.prepareStatement(query4, Statement.RETURN_GENERATED_KEYS)) {
                statement.setInt(1, userId);
                statement.executeUpdate();
                orderId = Mapper.getId(statement);
            }

            final int entryId;
            final String query5 = "INSERT into OrderEntries(BookId, Amount) " +
                                  "VALUES (?, 10)";
            try (final PreparedStatement statement = connection.prepareStatement(query5, Statement.RETURN_GENERATED_KEYS)) {
                statement.setInt(1, bookId);
                statement.executeUpdate();
                entryId = Mapper.getId(statement);
            }

            final String query6 = "INSERT into Cart VALUES (?, ?)";
            try (final PreparedStatement statement = connection.prepareStatement(query6, Statement.RETURN_GENERATED_KEYS)) {
                statement.setInt(1, orderId);
                statement.setInt(2, entryId);
                statement.executeUpdate();
            }
        }
    }

    @AfterClass
    public static void clearDatabase() throws SQLException {
        final TestConnectionManager manager = new TestConnectionManager();
        try (Connection connection = manager.getConnection("testdb")) {
            try (final Statement statement = connection.createStatement()) {
                final String query1 = "DELETE from Cart";
                statement.executeUpdate(query1);
                final String query2 = "DELETE from OrderEntries";
                statement.executeUpdate(query2);
                final String query3 = "DELETE from Orders";
                statement.executeUpdate(query3);
                final String query4 = "DELETE from Books";
                statement.executeUpdate(query4);
                final String query5 = "DELETE from Users";
                statement.executeUpdate(query5);
            }
        }
    }

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Test
    public void selectTest() throws Exception {
        final TestConnectionManager manager = new TestConnectionManager();
        try (Connection connection = manager.getConnection("testdb")) {
            final Mapper<Order> mapper = new OrderMapper(connection);
            final Order test = mapper.find(orderId);
            assertNotNull("Order not found", test);
            assertEquals("Incorrect customer", "Elvis", test.getCustomer().getName());
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
            final Order test = mapper.find(orderId);
            mapper.update(test);
        }
    }

    @Test
    public void insertTest() throws Exception {
        final TestConnectionManager manager = new TestConnectionManager();
        try (Connection connection = manager.getConnection("testdb")) {
            final Mapper<Order> orderMapper = new OrderMapper(connection);
            final int id = orderMapper.insert(createOrder(connection, "9992158107"));
            final Order test = orderMapper.find(id);
            assertNotNull("Order not found", test);
            assertEquals("Incorrect customer", "Elvis", test.getCustomer().getName());
            assertEquals("Incorrect cart size", 2, test.getCart().size());
            assertEquals("Incorrect price", 200 * 0.9 * 100 + 50 * 120.44, test.getPrice(), EPSILON);
        }
    }

    @Test
    public void deleteTest() throws Exception {
        final TestConnectionManager manager = new TestConnectionManager();
        try (Connection connection = manager.getConnection("testdb")) {
            final String query1 = "INSERT into OrderEntries(BookId, Amount) VALUES (?, 15)";
            final int entryId;
            try (final PreparedStatement statement = connection.prepareStatement(query1, Statement.RETURN_GENERATED_KEYS)) {
                statement.setInt(1, bookId);
                statement.executeUpdate();
                entryId = Mapper.getId(statement);
            }

            final Mapper<Order> orderMapper = new OrderMapper(connection);
            final Mapper<OrderEntry> entryMapper = new OrderEntryMapper(connection);
            final int id = orderMapper.insert(createOrder(connection, "097522980X"));

            final String query2 = "INSERT into Cart VALUES (?, ?)";
            try (final PreparedStatement statement = connection.prepareStatement(query2, Statement.RETURN_GENERATED_KEYS)) {
                statement.setInt(1, id);
                statement.setInt(2, entryId);
                statement.executeUpdate();
            }

            orderMapper.delete(id);
            final Order test = orderMapper.find(id);
            assertNull("Order was not deleted", test);
            final OrderEntry entryTest = entryMapper.find(entryId);
            assertNull("Entry was not deleted", entryTest);
        }
    }

    private Order createOrder(final Connection connection, final String isbn10)
            throws DataMapperException {
        final Mapper<User> userMapper = new UserMapper(connection);
        final Mapper<Publisher> pubMapper = new PublisherMapper(connection);
        final Mapper<Book> bookMapper = new BookMapper(connection);

        final User user = userMapper.find(userId);
        final Publisher pub = pubMapper.find(pubId);
        final Book book1 = bookMapper.find(bookId);
        final Book book2 = new Book.Builder("test",
                                            "",
                                            "",
                                            pub,
                                            new GregorianCalendar(),
                                            new Isbn(isbn10),
                                            120.44).build();
        book2.setId(bookMapper.insert(book2));

        final OrderEntry entry1 = new OrderEntry(-1, book1, 100);
        final OrderEntry entry2 = new OrderEntry(-1, book2, 50);
        final Cart cart = new Cart();
        cart.put(entry1);
        cart.put(entry2);

        return new Order.Builder(cart, (Customer)user).build();
    }
}
