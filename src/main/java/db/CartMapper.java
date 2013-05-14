package db;

import business.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.GregorianCalendar;

/**
 * Created with IntelliJ IDEA.
 * User: alexander
 * Date: 5/14/13
 * Time: 4:28 PM
 * To change this template use File | Settings | File Templates.
 */
public class CartMapper {
    private ConnectionManager connectionManager_;

    public CartMapper(ConnectionManager manager) {
        assert(manager != null);
        connectionManager_ = manager;
    }

    public Cart findByOrderId(int id) throws DataMapperException {
        Connection conn = null;
        try {
            conn = connectionManager_.getConnection();

            String query = "SELECT * from Cart where OrderId=?";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();

            Cart result = new Cart();

            while (rs.next()) {
                OrderEntryMapper mapper = new OrderEntryMapper(connectionManager_);
                OrderEntry entry = mapper.findById(rs.getInt("OrderEntryId"));
                if (entry == null) {
                    throw new DataMapperException("OrderEntry not found");
                }

                result.put(entry);
            }

            return result;
        } catch (SQLException e) {
            throw new DataMapperException("Error occurred while searching for book", e);
        } finally {
            if (conn != null) {
                try {
                    connectionManager_.closeConnection(conn);
                } catch (SQLException e2) {}
            }
        }
    }
}
