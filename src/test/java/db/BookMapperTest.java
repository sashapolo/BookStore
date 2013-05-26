package db;

import business.Book;
import business.Isbn10;
import business.Publisher;
import business.User;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.GregorianCalendar;
import java.util.List;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.*;

/**
 * Created with IntelliJ IDEA.
 * User: alexander
 * Date: 5/14/13
 * Time: 9:15 PM
 * To change this template use File | Settings | File Templates.
 */
public class BookMapperTest {
    private static int publisherId_;
    public static final double EPSILON = 1e-15;

    @BeforeClass
    public static void setUpDatabase() throws Exception {
        final TestConnectionManager manager = new TestConnectionManager();
        Statement statement = null;
        Connection connection = null;
        try {
            connection = manager.getConnection();
            String query = "INSERT into Users(Type, Login, Password, Name, SecondName, Email, PersonalDiscount)" +
                           "VALUES (2, 'foo', 0, 'Mad', 'Jack', 'The pirate', 0)";
            statement = connection.createStatement();
            statement.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);

            publisherId_ = Mapper.getId(statement);

            query = "INSERT into Books(Name, Genre, Isbn, PublicationDate, Price, Discount, NumSold, PublisherId)" +
                    "VALUES ('foo', 'bar', '9783161484100', '2012-01-01', 200, 10, 0, " + publisherId_ + ')';
            statement = connection.createStatement();
            statement.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);
        } finally {
            if (connection != null) connection.close();
            if (statement != null) statement.close();
        }
    }

    @AfterClass
    public static void clearDatabase() throws SQLException {
        final TestConnectionManager manager = new TestConnectionManager();
        Statement statement = null;
        Connection connection = null;
        try {
            connection = manager.getConnection();
            String query = "DELETE from Books";
            statement = connection.createStatement();
            statement.executeUpdate(query);
            query = "DELETE from Users";
            statement = connection.createStatement();
            statement.executeUpdate(query);
        } finally {
            if (connection != null) connection.close();
            if (statement != null) statement.close();
        }
    }

    @Test
    public void selectTest() throws Exception {
        final TestConnectionManager manager = new TestConnectionManager();
        Connection connection = null;
        try {
            connection = manager.getConnection();
            final BookMapper mapper = new BookMapper(connection);
            final List<Book> books = mapper.find("foo");
            assertEquals("Found incorrect number of books", 1, books.size());
            for (final Book book : books) {
                assertEquals("Wrong name", "foo", book.getName());
                assertEquals("Wrong genre", "bar", book.getGenre());
                assertEquals("Wrong isbn", "9783161484100", book.getIsbn().toString());
                assertEquals("Wrong price", 180, book.getPrice(), EPSILON);
                assertNotNull("Publisher not found", book.getPublisher());
                assertEquals("Incorrect publisher", publisherId_, book.getPublisher().getId());
            }
        } finally {
            if (connection != null) connection.close();
        }
    }

    @Test
    public void insertTest() throws Exception {
        final TestConnectionManager manager = new TestConnectionManager();
        Connection connection = null;
        try {
            connection = manager.getConnection();
            final UserMapper userMapper = new UserMapper(connection);
            final User publisher = userMapper.find(publisherId_);
            assertNotNull("Publisher not found", publisher);
            assertThat("Retrieved wrong user type", publisher, instanceOf(Publisher.class));

            final Book book = new Book.Builder(-1,
                                               "test",
                                               "",
                                               (Publisher)publisher,
                                               new GregorianCalendar(),
                                               new Isbn10("097522980X").toIsbn13(),
                                               120.44).build();
            final BookMapper bookMapper = new BookMapper(connection);
            final int id = bookMapper.insert(book);
            final Book check = bookMapper.find(id);
            assertNotNull("Inserted book not found", check);
            assertEquals("Wrong name of inserted book", "test", book.getName());
        } finally {
            if (connection != null) connection.close();
        }
    }

    @Test
    public void updateTest() throws Exception {
        final TestConnectionManager manager = new TestConnectionManager();
        Connection connection = null;
        try {
            connection = manager.getConnection();
            final UserMapper userMapper = new UserMapper(connection);
            final User publisher = userMapper.find(publisherId_);
            assertNotNull("Publisher not found", publisher);
            assertThat("Retrieved wrong user type", publisher, instanceOf(Publisher.class));

            final Book book = new Book.Builder(-1,
                                               "test",
                                               "",
                                               (Publisher)publisher,
                                               new GregorianCalendar(),
                                               new Isbn10("9992158107").toIsbn13(),
                                               120.44).build();
            final BookMapper bookMapper = new BookMapper(connection);
            final int id = bookMapper.insert(book);
            final Book test = new Book.Builder(id,
                                               "test69",
                                               "horror",
                                               (Publisher)publisher,
                                               new GregorianCalendar(),
                                               new Isbn10("9992158107").toIsbn13(),
                                               120.44).build();
            bookMapper.update(test);
            final Book check = bookMapper.find(id);
            assertNotNull("Updated book not found", check);
            assertEquals("Wrong name of updated book", "test69", check.getName());
            assertEquals("Wrong genre of updated book", "horror", check.getGenre());
        } finally {
            if (connection != null) connection.close();
        }
    }

    @Test
    public void deleteTest() throws Exception {
        final TestConnectionManager manager = new TestConnectionManager();
        Connection connection = null;
        try {
            connection = manager.getConnection();
            final UserMapper userMapper = new UserMapper(connection);
            final User publisher = userMapper.find(publisherId_);
            assertNotNull("Publisher not found", publisher);
            assertThat("Retrieved wrong user type", publisher, instanceOf(Publisher.class));

            final Book book = new Book.Builder(-1,
                                               "test",
                                               "",
                                               (Publisher)publisher,
                                               new GregorianCalendar(),
                                               new Isbn10("0123456789").toIsbn13(),
                                               120.44).build();
            final BookMapper bookMapper = new BookMapper(connection);
            final int id = bookMapper.insert(book);

            bookMapper.delete(id + 1);
            assertNotNull("Book was deleted", bookMapper.find(id));
            bookMapper.delete(id);
            assertNull("Book was not deleted", bookMapper.find(id));
        } finally {
            if (connection != null) connection.close();
        }
    }
}