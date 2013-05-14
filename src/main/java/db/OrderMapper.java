package db;

import business.Cart;
import business.Customer;
import business.Order;
import business.User;
import util.ReverseEnumMap;

import java.sql.*;
import java.util.GregorianCalendar;

/**
 * Created with IntelliJ IDEA.
 * User: alexander
 * Date: 5/14/13
 * Time: 1:57 PM
 * To change this template use File | Settings | File Templates.
 */
public class OrderMapper {
    private ConnectionManager connectionManager_;

    public OrderMapper(ConnectionManager manager) {
        assert(manager != null);
        connectionManager_ = manager;
    }

    public Order findById(int id) throws DataMapperException {
        Connection conn = null;
        try {
            conn = connectionManager_.getConnection();

            String query = "SELECT * from Orders where OrderId=?";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                GregorianCalendar date = new GregorianCalendar();
                date.setTime(rs.getDate("CreationDate"));

                ReverseEnumMap<Order.OrderStatus> reverse = new ReverseEnumMap<>(Order.OrderStatus.class);
                Order.OrderStatus status = reverse.get(rs.getInt("Status"));

                UserMapper userMapper = new UserMapper(connectionManager_);
                User orderer = userMapper.findById(rs.getInt("CustomerId"));
                if (orderer == null) {
                    throw new DataMapperException("Orderer not found");
                }
                if (!(orderer instanceof Customer)) {
                    throw new DataMapperException("Incorrect orderer type");
                }

                CartMapper cartMapper = new CartMapper(connectionManager_);
                Cart cart = cartMapper.findByOrderId(id);

                return new Order.Builder(id, cart, (Customer)orderer)
                                    .status(status)
                                    .dateCreated(date)
                                    .build();
            }

            return null;
        } catch (SQLException e) {
            throw new DataMapperException("Error occurred while searching for order", e);
        } finally {
            if (conn != null) {
                try {
                    connectionManager_.closeConnection(conn);
                } catch (SQLException e2) {}
            }
        }
    }

    public void insert(final Order order) throws DataMapperException {
        assert(order != null);

        Connection conn = null;
        try {
            conn = connectionManager_.getConnection();

            String query = "INSERT into Orders VALUES (?, ?, ?)";
            PreparedStatement statement = conn.prepareStatement(query);

            statement.setDate(1, new Date(order.getDateCreated().getTimeInMillis()));
            statement.setInt(2, order.getStatus().convert());
            statement.setInt(3, order.getOrderer().getId());

            statement.executeUpdate();

            ResultSet keys = statement.getGeneratedKeys();
            if (keys.next()) {
                order.setId(keys.getInt(1));
                CartMapper mapper = new CartMapper(connectionManager_);
                mapper.insertOrder(order);
            }
            throw new DataMapperException("Error occurred while retrieving primary key");
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

    public void delete(final Order order) throws DataMapperException {
        Connection conn = null;
        try {
            conn = connectionManager_.getConnection();

            String query = "DELETE from Orders where Id=?";
            PreparedStatement statement = conn.prepareStatement(query);

            statement.setInt(1, order.getId());

            statement.executeUpdate();
            CartMapper mapper = new CartMapper(connectionManager_);
            mapper.deleteOrder(order);
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
