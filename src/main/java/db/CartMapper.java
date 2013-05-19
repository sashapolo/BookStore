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

// TODO: this class is really fucked up because I can't find the way to implement it in terms of Mapper<Cart>.
public class CartMapper {
    private final Connection connection_;

    public CartMapper(final Connection connection) {
        assert(connection != null);
        connection_ = connection;
    }

    public Cart find(final int id) throws DataMapperException {
        PreparedStatement statement = null;
        try {
            final String query = "SELECT * from Cart where OrderId=?";
            statement = connection_.prepareStatement(query);
            statement.setInt(1, id);
            final ResultSet rs = statement.executeQuery();

            final Cart result = new Cart();
            final Mapper<OrderEntry> mapper = new OrderEntryMapper(connection_);
            while (rs.next()) {
                final OrderEntry entry = mapper.find(rs.getInt("OrderEntryId"));
                if (entry == null) {
                    throw new DataMapperException("OrderEntry not found");
                }
                result.put(entry);
            }

            return result;
        } catch (SQLException e) {
            throw new DataMapperException("Error occurred while searching for entry", e);
        } finally {
            try {
                if (statement != null) statement.close();
            } catch (SQLException e) {}
        }
    }

    public void insert(final Order order) throws DataMapperException {
        assert(order != null);

        PreparedStatement statement = null;
        try {
            final String query = "INSERT into Cart VALUES (?, ?)";
            statement = connection_.prepareStatement(query);
            statement.setInt(1, order.getId());

            final Mapper<OrderEntry> mapper = new OrderEntryMapper(connection_);
            for (final OrderEntry entry : order.getCart().values()) {
                final int entryId = mapper.insert(entry);
                statement.setInt(2, entryId);
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new DataMapperException("Error occurred while inserting an order", e);
        } finally {
            try {
                if (statement != null) statement.close();
            } catch (SQLException e) {}
        }
    }

    public void delete(final Order order) throws DataMapperException {
        assert(order != null);

        PreparedStatement statement = null;
        try {
            final String query = "DELETE from Cart where OrderId=?";
            statement = connection_.prepareStatement(query);
            statement.setInt(1, order.getId());
            statement.executeUpdate();

            final Mapper<OrderEntry> mapper = new OrderEntryMapper(connection_);
            for (final OrderEntry entry : order.getCart().values()) {
                mapper.delete(entry);
            }

        } catch (SQLException e) {
            throw new DataMapperException("Error occurred while deleting an order", e);
        } finally {
            try {
                if (statement != null) statement.close();
            } catch (SQLException e) {}
        }
    }
}
