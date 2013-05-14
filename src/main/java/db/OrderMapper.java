package db;

import business.*;
import util.ReverseEnumMap;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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

    public Order findById(int id) throws SQLException, DataMapperException {
        Connection conn = null;
        try {
            conn = connectionManager_.getConnection();

            String query = "SELECT * from Cart where OrderId=?";
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
