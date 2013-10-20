package db;

import business.BookStore;
import org.apache.derby.jdbc.EmbeddedDataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: alexander
 * Date: 5/13/13
 * Time: 4:21 PM
 * To change this template use File | Settings | File Templates.
 */
public final class DerbyConnectionManager implements ConnectionManager {
    private static final Logger LOGGER = Logger.getLogger(BookStore.class.getName());

    @Override
    public Connection getConnection(final String url) throws SQLException {
        try {
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
        } catch (ClassNotFoundException e) {
            LOGGER.severe("Can't load derby embedded driver");
            throw new IllegalStateException(e);
        }
        final EmbeddedDataSource ds = new EmbeddedDataSource();
        ds.setDatabaseName(url);
        final Connection result = ds.getConnection();
        result.setAutoCommit(true);
        return result;
    }

    @Override
    public void closeConnection(final Connection conn) throws SQLException {
        if (conn != null) {
            conn.close();
        }
    }
}
