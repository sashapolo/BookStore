package edu.db;

import business.Book;
import business.OrderEntry;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.Assert.*;

/**
 * Created with IntelliJ IDEA.
 * User: alexander
 * Date: 5/19/13
 * Time: 1:12 AM
 * To change this template use File | Settings | File Templates.
 */
public final class OrderEntryMapperTest {
    private static int bookId = -1;
    private static int entryId = -1;

    @BeforeClass
    public static void setUpDatabase() throws Exception {
        final TestConnectionManager manager = new TestConnectionManager();

        try (Connection connection = manager.getConnection("testdb")) {
            final String query1 = "INSERT into Publishers(Name) VALUES ('Mad Jack')";
            final int id;
            try (final Statement statement = connection.createStatement()) {
                statement.executeUpdate(query1, Statement.RETURN_GENERATED_KEYS);
                id = Mapper.getId(statement);
            }

            final String query2 = "INSERT into Books(Name, Author, Genre, Isbn, PublicationDate, Price, Discount, PublisherId)" +
                                  "VALUES ('foo', '', 'bar', '9783161484100', '2012-01-01', 200, 10, ?)";
            try (final PreparedStatement statement = connection.prepareStatement(query2, Statement.RETURN_GENERATED_KEYS)) {
                statement.setInt(1, id);
                statement.executeUpdate();
                bookId = Mapper.getId(statement);
            }

            final String query3 = "INSERT into OrderEntries(BookId, Amount) VALUES (?, 10)";
            try (final PreparedStatement statement = connection.prepareStatement(query3, Statement.RETURN_GENERATED_KEYS)) {
                statement.setInt(1, bookId);
                statement.executeUpdate();
                entryId = Mapper.getId(statement);
            }
        }
    }

    @AfterClass
    public static void clearDatabase() throws SQLException {
        final TestConnectionManager manager = new TestConnectionManager();
        try (Connection connection = manager.getConnection("testdb")) {
            try (final Statement statement = connection.createStatement()) {
                final String query1 = "DELETE from OrderEntries";
                final String query2 = "DELETE from Books";
                final String query3 = "DELETE from Users";
                statement.executeUpdate(query1);
                statement.executeUpdate(query2);
                statement.executeUpdate(query3);
            }
        }
    }

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Test
    public void selectTest() throws Exception {
        final TestConnectionManager manager = new TestConnectionManager();
        try (Connection connection = manager.getConnection("testdb")) {
            final OrderEntryMapper mapper = new OrderEntryMapper(connection);
            final OrderEntry entry = mapper.find(entryId);
            assertNotNull("Entry not found", entry);
            assertEquals("Incorrect amount", 10, entry.getAmount());
            assertNotNull("Book not found", entry.getBook());
            assertEquals("Incorrect book", bookId, entry.getBook().getId());
        }
    }

    @Test
    public void insertTest() throws Exception {
        final TestConnectionManager manager = new TestConnectionManager();
        try (Connection connection = manager.getConnection("testdb")) {
            final OrderEntryMapper mapper = new OrderEntryMapper(connection);
            final BookMapper bookMapper = new BookMapper(connection);
            final Book book = bookMapper.find(bookId);
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
            final Book book = bookMapper.find(bookId);

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
            final Book book = bookMapper.find(bookId);

            final OrderEntry entry = new OrderEntry(-1, book, 100);
            final int id = mapper.insert(entry);
            mapper.delete(id + 1);
            assertNotNull("Entry was deleted", mapper.find(id));
            mapper.delete(id);
            assertNull("Entry was not deleted", mapper.find(id));
        }
    }
}
