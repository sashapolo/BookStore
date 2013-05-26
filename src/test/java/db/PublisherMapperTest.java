/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package db;

import business.Publisher;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author alexander
 */
public class PublisherMapperTest {
    private static int id_;

    @BeforeClass
    public static void setUpDatabase() throws Exception {
        final TestConnectionManager manager = new TestConnectionManager();
        Statement statement = null;
        try (Connection connection = manager.getConnection()) {
            final String query = "INSERT into Publishers(Name) VALUES ('Mad Jack')";
            statement = connection.createStatement();
            statement.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);
            id_ = Mapper.getId(statement);
        } finally {
            if (statement != null) statement.close();
        }
    }

    @AfterClass
    public static void clearDatabase() throws SQLException {
        final TestConnectionManager manager = new TestConnectionManager();
        Statement statement = null;
        try (Connection connection = manager.getConnection()) {
            final String query = "DELETE from Users";
            statement = connection.createStatement();
            statement.executeUpdate(query);
        } finally {
            if (statement != null) statement.close();
        }
    }

    @Test
    public void selectTest() throws Exception {
        final TestConnectionManager manager = new TestConnectionManager();
        try (Connection connection = manager.getConnection()) {
            final Mapper<Publisher> mapper = new PublisherMapper(connection);

            final Publisher pub = mapper.find(id_);
            assertEquals("Incorrect name", "Mad Jack", pub.getName());
        }
    }

    @Test
    public void updateTest() throws Exception {
        final TestConnectionManager manager = new TestConnectionManager();
        try (Connection connection = manager.getConnection()) {
            final Mapper<Publisher> mapper = new PublisherMapper(connection);

            final Publisher pub = new Publisher(-1, "spam");
            final int id = mapper.insert(pub);
            final Publisher tmp = new Publisher(id, "ham");
            mapper.update(tmp);
            final Publisher test = mapper.find(id);
            assertEquals("Incorrect name", "ham", test.getName());
        }
    }

    @Test
    public void deleteTest() throws Exception {
        final TestConnectionManager manager = new TestConnectionManager();
        try (Connection connection = manager.getConnection()) {
            final Mapper<Publisher> mapper = new PublisherMapper(connection);

            final Publisher pub = new Publisher(-1, "spam");
            final int id = mapper.insert(pub);
            mapper.delete(id + 1);
            assertNotNull("Publisher was deleted", mapper.find(id));
            mapper.delete(id);
            assertNull("Publisher was not deleted", mapper.find(id));
        }
    }
}