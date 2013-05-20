package db;

import business.Book;
import business.Order;
import business.OrderEntry;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.Assert.assertNotNull;

/**
 * Created with IntelliJ IDEA.
 * User: alexander
 * Date: 5/21/13
 * Time: 12:32 AM
 * To change this template use File | Settings | File Templates.
 */
public class OrderMapperTest {
    private static int orderId_;

    @BeforeClass
    public static void setUpDatabase() throws Exception {
        final TestConnectionManager manager = new TestConnectionManager();
        Statement statement = null;
        Connection connection = null;
        try {
            connection = manager.getConnection();

            String query = "INSERT into Users(Type, Login, Password, Name, SecondName, Email, PersonalDiscount) " +
                    "VALUES (2, 'foo', 0, 'Mad', 'Jack', 'The pirate', 0)";
            statement = connection.createStatement();
            statement.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);
            final int pubId = Mapper.getId(statement);

            query = "INSERT into Users(Type, Login, Password, Name, SecondName, Email, PersonalDiscount) " +
                    "VALUES (0, 'baz', 0, 'Elvis', 'Presley', 'Baby', 0)";
            statement = connection.createStatement();
            statement.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);
            final int userId = Mapper.getId(statement);

            query = "INSERT into Books(Name, Genre, Isbn, PublicationDate, Price, Discount, NumSold, PublisherId) " +
                    "VALUES ('foo', 'bar', '9783161484100', '2012-01-01', 200, 10, 0, " + pubId + ')';
            statement = connection.createStatement();
            statement.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);
            final int bookId = Mapper.getId(statement);

            query = "INSERT into Orders (CreationDate, Status, CustomerId) " +
                    "VALUES ('2012-01-01', 0, " + userId + ')';
            statement = connection.createStatement();
            statement.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);
            orderId_ = Mapper.getId(statement);

            query = "INSERT into OrderEntries(BookId, Amount) " +
                    "VALUES (" + bookId + ", 10)";
            statement = connection.createStatement();
            statement.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);
            final int entryId = Mapper.getId(statement);

            query = "INSERT into Cart " +
                    "VALUES (" + orderId_ + ", " + entryId + ')';
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
            String query = "DELETE from Cart";
            statement = connection.createStatement();
            statement.executeUpdate(query);
            query = "DELETE from OrderEntries";
            statement = connection.createStatement();
            statement.executeUpdate(query);
            query = "DELETE from Orders";
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
            final Mapper<Order> mapper = new OrderMapper(connection);
            final Order test = mapper.find(orderId_);
            assertNotNull("Order not found", test);
        } finally {
            if (connection != null) connection.close();
        }
    }
}
