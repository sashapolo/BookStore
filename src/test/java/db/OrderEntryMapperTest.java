package db;

import business.*;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.sql.*;
import java.util.GregorianCalendar;
import java.util.List;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.*;

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

    private static int getPrimaryKey(final Statement statement) throws Exception {
        ResultSet keys = null;
        try {
            keys = statement.getGeneratedKeys();
            if (keys.next()) {
                return keys.getInt(1);
            } else {
                throw new Exception("Can't get primary key id");
            }
        } finally {
            if (keys != null) keys.close();
        }
    }

    @Rule
    public final ExpectedException exception = ExpectedException.none();

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

            final int id = getPrimaryKey(statement);

            query = "INSERT into Books(Name, Genre, Isbn, PublicationDate, Price, Discount, NumSold, PublisherId)" +
                    "VALUES ('foo', 'bar', '9783161484100', '2012-01-01', 200, 10, 0, " + id + ')';
            statement = connection.createStatement();
            statement.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);

            bookId_ = getPrimaryKey(statement);

            query = "INSERT into OrderEntries(BookId, Amount)" +
                    "VALUES (" + bookId_+ ", 10)";
            statement = connection.createStatement();
            statement.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);

            entryId_ = getPrimaryKey(statement);
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
            final OrderEntryMapper mapper = new OrderEntryMapper(connection);
            final OrderEntry entry = mapper.find(entryId_);
            assertNotNull("Entry not found", entry);
            assertEquals("Incorrect amount", 10, entry.getAmount());
            assertNotNull("Book not found", entry.getBook());
            assertEquals("Incorrect book", bookId_, entry.getBook().getId());
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
            final OrderEntryMapper mapper = new OrderEntryMapper(connection);
            final BookMapper bookMapper = new BookMapper(connection);
            final Book book = bookMapper.find(bookId_);
            assertNotNull("Book not found", book);

            final OrderEntry entry = new OrderEntry(-1, book, 100);
            final int id = mapper.insert(entry);
            final OrderEntry test = mapper.find(id);
            assertNotNull("Entry not found", test);
            assertEquals("Incorrect amount", 100, test.getAmount());
        } finally {
            if (connection != null) connection.close();
        }
    }

    @Test
    public void updateTest() throws Exception {
        exception.expect(DataMapperException.class);
        exception.expectMessage("Orders should never be updated!");

        final TestConnectionManager manager = new TestConnectionManager();
        Connection connection = null;
        try {
            connection = manager.getConnection();
            final OrderEntryMapper mapper = new OrderEntryMapper(connection);
            final BookMapper bookMapper = new BookMapper(connection);
            final Book book = bookMapper.find(bookId_);

            final OrderEntry entry = new OrderEntry(-1, book, 100);
            mapper.update(entry);
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
            final OrderEntryMapper mapper = new OrderEntryMapper(connection);
            final BookMapper bookMapper = new BookMapper(connection);
            final Book book = bookMapper.find(bookId_);

            final OrderEntry entry = new OrderEntry(-1, book, 100);
            final int id = mapper.insert(entry);
            mapper.delete(id + 1);
            assertNotNull("Entry was deleted", mapper.find(id));
            mapper.delete(id);
            assertNull("Entry was not deleted", mapper.find(id));
        } finally {
            if (connection != null) connection.close();
        }
    }
}
