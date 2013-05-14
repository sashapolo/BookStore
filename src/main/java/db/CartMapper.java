package db;

import business.Cart;
import business.Order;
import business.OrderEntry;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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

            OrderEntryMapper mapper = new OrderEntryMapper(connectionManager_);
            while (rs.next()) {
                OrderEntry entry = mapper.findById(rs.getInt("OrderEntryId"));
                if (entry == null) {
                    throw new DataMapperException("OrderEntry not found");
                }

                result.put(entry);
            }

            return result;
        } catch (SQLException e) {
            throw new DataMapperException("Error occurred while searching for entry", e);
        } finally {
            if (conn != null) {
                try {
                    connectionManager_.closeConnection(conn);
                } catch (SQLException e2) {}
            }
        }
    }

    public void insertOrder(final Order order) throws DataMapperException {
        assert(order != null);

        Connection conn = null;
        try {
            conn = connectionManager_.getConnection();

            String query = "INSERT into Cart VALUES (?, ?)";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setInt(1, order.getId());

            OrderEntryMapper mapper = new OrderEntryMapper(connectionManager_);
            for (OrderEntry entry : order.getCart().values()) {
                int entryId = mapper.insert(entry);
                statement.setInt(2, entryId);
                statement.executeUpdate();
            }

        } catch (SQLException e) {
            throw new DataMapperException("Error occurred while inserting an order", e);
        } finally {
            if (conn != null) {
                try {
                    connectionManager_.closeConnection(conn);
                } catch (SQLException e2) {}
            }
        }
    }

    public void deleteOrder(final Order order) throws DataMapperException {
        assert(order != null);

        Connection conn = null;
        try {
            conn = connectionManager_.getConnection();

            String query = "DELETE from Cart where OrderId=?";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setInt(1, order.getId());
            statement.executeUpdate();

            OrderEntryMapper mapper = new OrderEntryMapper(connectionManager_);
            for (OrderEntry entry : order.getCart().values()) {
                mapper.delete(entry);
            }

        } catch (SQLException e) {
            throw new DataMapperException("Error occurred while deleting an order", e);
        } finally {
            if (conn != null) {
                try {
                    connectionManager_.closeConnection(conn);
                } catch (SQLException e2) {}
            }
        }
    }
}
