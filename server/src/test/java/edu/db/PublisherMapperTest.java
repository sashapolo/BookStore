/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.db;

import edu.business.Publisher;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.Assert.*;

/**
 *
 * @author alexander
 */
public final class PublisherMapperTest {
    private static int id = -1;

    @BeforeClass
    public static void setUpDatabase() throws Exception {
        final TestConnectionManager manager = new TestConnectionManager();
        try (Connection connection = manager.getConnection("testdb")) {
            final String query = "INSERT into Publishers(Name) VALUES ('Mad Jack')";
            try (final Statement statement = connection.createStatement()) {
                statement.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);
                id = Mapper.getId(statement);
            }
        }
    }

    @AfterClass
    public static void clearDatabase() throws SQLException {
        final TestConnectionManager manager = new TestConnectionManager();
        try (Connection connection = manager.getConnection("testdb")) {
            final String query = "DELETE from Users";
            try (final Statement statement = connection.createStatement()) {
                statement.executeUpdate(query);
            }
        }
    }

    @Test
    public void selectTest() throws Exception {
        final TestConnectionManager manager = new TestConnectionManager();
        try (Connection connection = manager.getConnection("testdb")) {
            final Mapper<Publisher> mapper = new PublisherMapper(connection);
            final Publisher pub = mapper.find(id);
            assertEquals("Incorrect name", "Mad Jack", pub.getName());
        }
    }

    @Test
    public void updateTest() throws Exception {
        final TestConnectionManager manager = new TestConnectionManager();
        try (Connection connection = manager.getConnection("testdb")) {
            final Mapper<Publisher> mapper = new PublisherMapper(connection);

            final Publisher pub = new Publisher(-1, "spam");
            final int i = mapper.insert(pub);
            final Publisher tmp = new Publisher(i, "ham");
            mapper.update(tmp);
            final Publisher test = mapper.find(i);
            assertEquals("Incorrect name", "ham", test.getName());
        }
    }

    @Test
    public void deleteTest() throws Exception {
        final TestConnectionManager manager = new TestConnectionManager();
        try (Connection connection = manager.getConnection("testdb")) {
            final Mapper<Publisher> mapper = new PublisherMapper(connection);

            final Publisher pub = new Publisher(-1, "spam");
            final int i = mapper.insert(pub);
            mapper.delete(i + 1);
            assertNotNull("Publisher was deleted", mapper.find(i));
            mapper.delete(i);
            assertNull("Publisher was not deleted", mapper.find(i));
        }
    }
}
