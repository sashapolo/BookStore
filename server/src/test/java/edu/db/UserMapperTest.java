package edu.db;

import edu.business.Administrator;
import edu.business.Credentials;
import edu.business.Customer;
import edu.business.User;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.*;

/**
 * Created with IntelliJ IDEA.
 * User: alexander
 * Date: 5/19/13
 * Time: 2:31 PM
 * To change this template use File | Settings | File Templates.
 */
public final class UserMapperTest {
    private static int customerId_ = -1;
    private static int administratorId_ = -1;

    @BeforeClass
    public static void setUpDatabase() throws Exception {
        final TestConnectionManager manager = new TestConnectionManager();
        try (Connection connection = manager.getConnection("testdb")) {
            final String query1 = "INSERT into Users(Type, Login, Password, Name, SecondName, Email, PersonalDiscount)" +
                                  "VALUES (1, 'bar', 0, 'Led', 'Zeppelin', 'IV', 0)";
            try (final Statement statement = connection.createStatement()) {
                statement.executeUpdate(query1, Statement.RETURN_GENERATED_KEYS);
                administratorId_ = Mapper.getId(statement);
            }

            final String query2 = "INSERT into Users(Type, Login, Password, Name, SecondName, Email, PersonalDiscount)" +
                                  "VALUES (0, 'baz', 0, 'Elvis', 'Presley', 'Baby', 0)";
            try (final Statement statement = connection.createStatement()) {
                statement.executeUpdate(query2, Statement.RETURN_GENERATED_KEYS);
                customerId_ = Mapper.getId(statement);
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
            final UserMapper mapper = new UserMapper(connection);

            final User admin = mapper.find(administratorId_);
            assertThat("Got incorrect type from the database", admin, instanceOf(Administrator.class));
            assertEquals("Incorrect name", "bar", admin.getLogin());

            final User custom = mapper.find(customerId_);
            assertThat("Got incorrect type from the database", custom, instanceOf(Customer.class));
            assertEquals("Incorrect name", "baz", custom.getLogin());
        }
    }

    @Test
    public void updateTest() throws Exception {
        final TestConnectionManager manager = new TestConnectionManager();
        try (Connection connection = manager.getConnection("testdb")) {
            final UserMapper mapper = new UserMapper(connection);

            final User admin = new Administrator(-1, "spam", 0, new Credentials("", "", ""));
            final int id = mapper.insert(admin);
            final User tmp = new Administrator(id, "ham", 0, new Credentials("", "", ""));
            mapper.update(tmp);
            final User test = mapper.find(id);
            assertThat("Got incorrect type from the database", test, instanceOf(Administrator.class));
            assertEquals("Incorrect name", "ham", test.getLogin());
        }
    }

    @Test
    public void deleteTest() throws Exception {
        final TestConnectionManager manager = new TestConnectionManager();
        try (Connection connection = manager.getConnection("testdb")) {
            final UserMapper mapper = new UserMapper(connection);

            final User admin = new Administrator(-1, "spam", 0, new Credentials("", "", ""));
            final int id = mapper.insert(admin);
            mapper.delete(id + 1);
            assertNotNull("User was deleted", mapper.find(id));
            mapper.delete(id);
            assertNull("User was not deleted", mapper.find(id));
        }
    }
}
