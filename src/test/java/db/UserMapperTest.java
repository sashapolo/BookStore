package db;

import business.*;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.*;

/**
 * Created with IntelliJ IDEA.
 * User: alexander
 * Date: 5/19/13
 * Time: 2:31 PM
 * To change this template use File | Settings | File Templates.
 */
public class UserMapperTest {
    private static int publisherId_;
    private static int customerId_;
    private static int administratorId_;

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
            publisherId_ = getPrimaryKey(statement);

            query = "INSERT into Users(Type, Login, Password, Name, SecondName, Email, PersonalDiscount)" +
                    "VALUES (1, 'bar', 0, 'Led', 'Zeppelin', 'IV', 0)";
            statement = connection.createStatement();
            statement.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);
            administratorId_ = getPrimaryKey(statement);

            query = "INSERT into Users(Type, Login, Password, Name, SecondName, Email, PersonalDiscount)" +
                    "VALUES (0, 'baz', 0, 'Elvis', 'Presley', 'Baby', 0)";
            statement = connection.createStatement();
            statement.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);
            customerId_ = getPrimaryKey(statement);
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
            final String query = "DELETE from Users";
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
            final UserMapper mapper = new UserMapper(connection);

            final User admin = mapper.find(administratorId_);
            assertThat("Got incorrect type from the database", admin, instanceOf(Administrator.class));
            assertEquals("Incorrect name", "bar", admin.getLogin());

            final User custom = mapper.find(customerId_);
            assertThat("Got incorrect type from the database", custom, instanceOf(Customer.class));
            assertEquals("Incorrect name", "baz", custom.getLogin());

            final User pub = mapper.find(publisherId_);
            assertThat("Got incorrect type from the database", pub, instanceOf(Publisher.class));
            assertEquals("Incorrect name", "foo", pub.getLogin());
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
            final UserMapper mapper = new UserMapper(connection);

            final User pub = new Publisher(-1, "spam", 0, "", "", "");
            final int id = mapper.insert(pub);
            final User tmp = new Publisher(id, "ham", 0, "", "", "");
            mapper.update(tmp);
            final User test = mapper.find(id);
            assertThat("Got incorrect type from the database", test, instanceOf(Publisher.class));
            assertEquals("Incorrect name", "ham", test.getLogin());
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
            final UserMapper mapper = new UserMapper(connection);

            final User pub = new Publisher(-1, "spam", 0, "", "", "");
            final int id = mapper.insert(pub);
            mapper.delete(id + 1);
            assertNotNull("User was deleted", mapper.find(id));
            mapper.delete(id);
            assertNull("User was not deleted", mapper.find(id));
        } finally {
            if (connection != null) connection.close();
        }
    }
}
