package db;

import business.Book;
import business.Isbn;
import business.Publisher;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
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
public final class BookMapperTest {
    private static int publisherId = -1;
    private static final double EPSILON = 1e-15;

    @BeforeClass
    public static void setUpDatabase() throws Exception {
        final TestConnectionManager manager = new TestConnectionManager();
        try (Connection connection = manager.getConnection("testdb")) {
            final String query1 = "INSERT into Publishers(Name) VALUES ('Mad Jack')";
            try (final Statement statement = connection.createStatement()) {
                statement.executeUpdate(query1, Statement.RETURN_GENERATED_KEYS);
                publisherId = Mapper.getId(statement);
            }

            final String query2 = "INSERT into Books(Name, Author, Genre, Isbn, PublicationDate, Price, Discount, PublisherId)" +
                                  "VALUES ('foo', '', 'bar', '9783161484100', '2012-01-01', 200, 10, ?)";
            try (final PreparedStatement statement = connection.prepareStatement(query2, Statement.RETURN_GENERATED_KEYS)) {
                statement.setInt(1, publisherId);
                statement.executeUpdate();
            }
        }
    }

    @AfterClass
    public static void clearDatabase() throws SQLException {
        final TestConnectionManager manager = new TestConnectionManager();
        try (Connection connection = manager.getConnection("testdb")) {
            final String query1 = "DELETE from Books";
            try (final Statement statement = connection.createStatement()) {
                statement.executeUpdate(query1);
            }

            final String query2 = "DELETE from Users";
            try (final Statement statement = connection.createStatement()) {
                statement.executeUpdate(query2);
            }
        }
    }

    @Test
    public void selectTest() throws Exception {
        final TestConnectionManager manager = new TestConnectionManager();
        try (Connection connection = manager.getConnection("testdb")) {
            final BookMapper mapper = new BookMapper(connection);
            final List<Book> books = mapper.find("foo");
            assertEquals("Found incorrect number of books", 1, books.size());
            for (final Book book : books) {
                assertEquals("Wrong name", "foo", book.getName());
                assertEquals("Wrong genre", "bar", book.getGenre());
                assertEquals("Wrong isbn", "9783161484100", book.getIsbn().toString());
                assertEquals("Wrong price", 180, book.getPrice(), EPSILON);
                assertNotNull("Publisher not found", book.getPublisher());
                assertEquals("Incorrect publisher", publisherId, book.getPublisher().getId());
            }
        }
    }

    @Test
    public void insertTest() throws Exception {
        final TestConnectionManager manager = new TestConnectionManager();
        try (Connection connection = manager.getConnection("testdb")) {
            final Mapper<Publisher> pubMapper = new PublisherMapper(connection);
            final Publisher publisher = pubMapper.find(publisherId);
            assertNotNull("Publisher not found", publisher);
            assertThat("Retrieved wrong user type", publisher, instanceOf(Publisher.class));

            final Book book = new Book.Builder("test",
                                               "",
                                               "",
                                               publisher,
                                               new GregorianCalendar(),
                                               new Isbn("097522980X"),
                                               120.44).build();
            final BookMapper bookMapper = new BookMapper(connection);
            final int id = bookMapper.insert(book);
            final Book check = bookMapper.find(id);
            assertNotNull("Inserted book not found", check);
            assertEquals("Wrong name of inserted book", "test", book.getName());
        }
    }

    @Test
    public void updateTest() throws Exception {
        final TestConnectionManager manager = new TestConnectionManager();
        try (Connection connection = manager.getConnection("testdb")) {
            final Mapper<Publisher> pubMapper = new PublisherMapper(connection);
            final Publisher publisher = pubMapper.find(publisherId);
            assertNotNull("Publisher not found", publisher);
            assertThat("Retrieved wrong user type", publisher, instanceOf(Publisher.class));

            final Book book = new Book.Builder("test",
                                               "",
                                               "",
                                               publisher,
                                               new GregorianCalendar(),
                                               new Isbn("9992158107"),
                                               120.44).build();
            final BookMapper bookMapper = new BookMapper(connection);
            final int id = bookMapper.insert(book);
            final Book test = new Book.Builder("test69",
                                               "",
                                               "horror",
                                               publisher,
                                               new GregorianCalendar(),
                                               new Isbn("9992158107"),
                                               120.44).id(id).build();
            bookMapper.update(test);
            final Book check = bookMapper.find(id);
            assertNotNull("Updated book not found", check);
            assertEquals("Wrong name of updated book", "test69", check.getName());
            assertEquals("Wrong genre of updated book", "horror", check.getGenre());
        }
    }

    @Test
    public void deleteTest() throws Exception {
        final TestConnectionManager manager = new TestConnectionManager();
        try (Connection connection = manager.getConnection("testdb")) {
            final Mapper<Publisher> pubMapper = new PublisherMapper(connection);
            final Publisher publisher = pubMapper.find(publisherId);
            assertNotNull("Publisher not found", publisher);
            assertThat("Retrieved wrong user type", publisher, instanceOf(Publisher.class));

            final Book book = new Book.Builder("test",
                                               "",
                                               "",
                                               publisher,
                                               new GregorianCalendar(),
                                               new Isbn("0123456789"),
                                               120.44).build();
            final BookMapper bookMapper = new BookMapper(connection);
            final int id = bookMapper.insert(book);

            bookMapper.delete(id + 1);
            assertNotNull("Book was deleted", bookMapper.find(id));
            bookMapper.delete(id);
            assertNull("Book was not deleted", bookMapper.find(id));
        }
    }
}
