package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created with IntelliJ IDEA.
 * User: alexander
 * Date: 5/14/13
 * Time: 9:02 PM
 * To change this template use File | Settings | File Templates.
 */
public class TestConnectionManager implements ConnectionManager {
    @Override
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:derby:testdb;create=true");
    }

    @Override
    public void closeConnection(Connection conn) throws SQLException {
        if (conn != null) {
            conn.close();
            conn = null;
        }
    }
}
