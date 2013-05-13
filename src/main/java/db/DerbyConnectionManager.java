package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created with IntelliJ IDEA.
 * User: alexander
 * Date: 5/13/13
 * Time: 4:21 PM
 * To change this template use File | Settings | File Templates.
 */
public class DerbyConnectionManager implements ConnectionManager {
    private final String dbUrl_;
    private final String username_;
    private final String password_;

    public DerbyConnectionManager(final String dbUrl, final String username, final String password) {
        assert(dbUrl != null);
        assert(username != null);
        assert(password != null);

        dbUrl_ = dbUrl;
        username_ = username;
        password_ = password;
    }

    @Override
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(dbUrl_ + ";create=true", username_, password_);
    }

    @Override
    public void closeConnection(Connection conn) throws SQLException {
        if (conn != null) {
            conn.close();
            conn = null;
        }
    }
}
