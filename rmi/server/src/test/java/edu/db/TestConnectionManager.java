package edu.db;

import org.apache.derby.jdbc.EmbeddedDataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: alexander
 * Date: 5/14/13
 * Time: 9:02 PM
 * To change this template use File | Settings | File Templates.
 */
public final class TestConnectionManager implements ConnectionManager {
    private static final Logger LOGGER = Logger.getLogger(ConnectionManager.class.getName());

    @Override
    public Connection getConnection(final String url) throws SQLException {
        try {
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
        } catch (ClassNotFoundException e) {
            LOGGER.severe("Can't load derby embedded driver");
        }
        final EmbeddedDataSource ds = new EmbeddedDataSource();
        ds.setDatabaseName("testdb");
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
