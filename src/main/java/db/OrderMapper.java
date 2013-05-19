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
public class OrderMapper extends Mapper<Order> {

    public OrderMapper(final Connection connection) {
        super(connection);
    }

    @Override
    public Order find(final int id) throws DataMapperException {
        PreparedStatement statement = null;
        try {
            final String query = "SELECT * from Orders where OrderId=?";
            statement = connection_.prepareStatement(query);
            statement.setInt(1, id);

            final ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                final GregorianCalendar date = new GregorianCalendar();
                date.setTime(rs.getDate("CreationDate"));

                final ReverseEnumMap<Order.OrderStatus> reverse = new ReverseEnumMap<>(Order.OrderStatus.class);
                final Order.OrderStatus status = reverse.get(rs.getInt("Status"));

                final Mapper<User> userMapper = new UserMapper(connection_);
                final User orderer = userMapper.find(rs.getInt("CustomerId"));
                if (orderer == null) {
                    throw new DataMapperException("Orderer not found");
                }

                assert (orderer instanceof Customer);

                final CartMapper cartMapper = new CartMapper(connection_);
                final Cart cart = cartMapper.find(id);
                return new Order.Builder(id, cart, (Customer)orderer)
                                    .status(status)
                                    .dateCreated(date)
                                    .build();
            }

            return null;
        } catch (SQLException e) {
            throw new DataMapperException("Error occurred while searching for order", e);
        } finally {
            try {
                if (statement != null) statement.close();
            } catch (SQLException e) {}
        }
    }

    @Override
    public int insert(final Order order) throws DataMapperException {
        assert(order != null);

        PreparedStatement statement = null;
        try {
            final String query = "INSERT into Orders VALUES (?, ?, ?)";
            statement = connection_.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

            statement.setDate(1, new Date(order.getDateCreated().getTimeInMillis()));
            statement.setInt(2, order.getStatus().convert());
            statement.setInt(3, order.getOrderer().getId());
            statement.executeUpdate();

            int id = getId(statement);
            order.setId(id);
            final CartMapper mapper = new CartMapper(connection_);
            mapper.insert(order);
            return id;
        } catch (SQLException e) {
            throw new DataMapperException("Error occurred while inserting an order", e);
        } finally {
            try {
                if (statement != null) statement.close();
            } catch (SQLException e) {}
        }
    }

    @Override
    public void delete(final Order order) throws DataMapperException {
        PreparedStatement statement = null;
        try {
            final String query = "DELETE from Orders where Id=?";
            statement = connection_.prepareStatement(query);
            statement.setInt(1, order.getId());

            statement.executeUpdate();
            final CartMapper mapper = new CartMapper(connection_);
            mapper.delete(order);
        } catch (SQLException e) {
            throw new DataMapperException("Error occurred while deleting an order", e);
        } finally {
            try {
                if (statement != null) statement.close();
            } catch (SQLException e) {}
        }
    }

    @Override
    public void update(final Order order) throws DataMapperException {
        throw new DataMapperException("Orders should never be updated!");
    }
}
