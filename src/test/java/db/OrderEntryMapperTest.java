package db;

import business.Book;
import business.OrderEntry;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 * Created with IntelliJ IDEA.
 * User: alexander
 * Date: 5/19/13
 * Time: 1:12 AM
 * To change this template use File | Settings | File Templates.
 */
public class OrderEntryMapperTest {
    private static int bookId_;
    private static int entryId_;

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

            final int id = Mapper.getId(statement);

            query = "INSERT into Books(Name, Author, Genre, Isbn, PublicationDate, Price, Discount, PublisherId)" +
                    "VALUES ('foo', '', 'bar', '9783161484100', '2012-01-01', 200, 10, " + id + ')';
            statement = connection.createStatement();
            statement.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);

            bookId_ = Mapper.getId(statement);

            query = "INSERT into OrderEntries(BookId, Amount)" +
                    "VALUES (" + bookId_+ ", 10)";
            statement = connection.createStatement();
            statement.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);

            entryId_ = Mapper.getId(statement);
        } finally {
            if (statement != null) statement.close();
        }
    }

    @AfterClass
    public static void clearDatabase() throws SQLException {
        final TestConnectionManager manager = new TestConnectionManager();
        Statement statement = null;
        try (Connection connection = manager.getConnection("testdb")) {
            String query = "DELETE from OrderEntries";
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
            final OrderEntryMapper mapper = new OrderEntryMapper(connection);
            final OrderEntry entry = mapper.find(entryId_);
            assertNotNull("Entry not found", entry);
            assertEquals("Incorrect amount", 10, entry.getAmount());
            assertNotNull("Book not found", entry.getBook());
            assertEquals("Incorrect book", bookId_, entry.getBook().getId());
        }
    }

    @Test
    public void insertTest() throws Exception {
        final TestConnectionManager manager = new TestConnectionManager();
        try (Connection connection = manager.getConnection("testdb")) {
            final OrderEntryMapper mapper = new OrderEntryMapper(connection);
            final BookMapper bookMapper = new BookMapper(connection);
            final Book book = bookMapper.find(bookId_);
            assertNotNull("Book not found", book);

            final OrderEntry entry = new OrderEntry(-1, book, 100);
            final int id = mapper.insert(entry);
            final OrderEntry test = mapper.find(id);
            assertNotNull("Entry not found", test);
            assertEquals("Incorrect amount", 100, test.getAmount());
        }
    }

    @Test
    public void updateTest() throws Exception {
        exception.expect(DataMapperException.class);
        exception.expectMessage("Orders should never be updated!");

        final TestConnectionManager manager = new TestConnectionManager();
        try (Connection connection = manager.getConnection("testdb")) {
            final OrderEntryMapper mapper = new OrderEntryMapper(connection);
            final BookMapper bookMapper = new BookMapper(connection);
            final Book book = bookMapper.find(bookId_);

            final OrderEntry entry = new OrderEntry(-1, book, 100);
            mapper.update(entry);
        }
    }

    @Test
    public void deleteTest() throws Exception {
        final TestConnectionManager manager = new TestConnectionManager();
        try (Connection connection = manager.getConnection("testdb")) {
            final OrderEntryMapper mapper = new OrderEntryMapper(connection);
            final BookMapper bookMapper = new BookMapper(connection);
            final Book book = bookMapper.find(bookId_);

            final OrderEntry entry = new OrderEntry(-1, book, 100);
            final int id = mapper.insert(entry);
            mapper.delete(id + 1);
            assertNotNull("Entry was deleted", mapper.find(id));
            mapper.delete(id);
            assertNull("Entry was not deleted", mapper.find(id));
        }
    }
}
