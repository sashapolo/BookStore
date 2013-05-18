package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

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

    private static final Logger LOGGER = Logger.getLogger(DerbyConnectionManager.class.getName());

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
        try {
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
        } catch (ClassNotFoundException e) {
            LOGGER.severe("Can't load derby embedded driver");
        }
        return DriverManager.getConnection(dbUrl_ + ";create=true", username_, password_);
    }

    @Override
    public void closeConnection(final Connection conn) throws SQLException {
        if (conn != null) {
            conn.close();
        }
    }
}
