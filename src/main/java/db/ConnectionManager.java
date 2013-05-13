package db;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created with IntelliJ IDEA.
 * User: alexander
 * Date: 5/13/13
 * Time: 6:56 PM
 * To change this template use File | Settings | File Templates.
 */
public interface ConnectionManager {
    public Connection getConnection() throws SQLException;
    public void closeConnection(Connection conn) throws SQLException;
}
