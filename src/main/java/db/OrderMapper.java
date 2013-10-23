package db;

import business.*;
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
public final class OrderMapper extends Mapper<Order> {
    public OrderMapper(final Connection connection) {
        super(connection);
    }

    @Override
    public Order find(final int id) throws DataMapperException {
        final String query = "SELECT * from Orders where Id=?";
        try (final PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);

            final ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                final GregorianCalendar date = new GregorianCalendar();
                date.setTime(rs.getDate("CreationDate"));

                final ReverseEnumMap<Order.OrderStatus> reverse = new ReverseEnumMap<>(Order.OrderStatus.class);
                final Order.OrderStatus status = reverse.get(rs.getInt("Status"));

                final Mapper<User> userMapper = new UserMapper(connection);
                final User customer = userMapper.find(rs.getInt("CustomerId"));
                if (customer == null) {
                    throw new DataMapperException("Customer not found");
                }

                assert customer instanceof Customer;

                final Cart cart = getCart(id);
                return new Order.Builder(cart, (Customer)customer)
                                    .id(id)
                                    .status(status)
                                    .dateCreated(date)
                                    .build();
            }

            return null;
        } catch (SQLException e) {
            throw new DataMapperException("Error occurred while searching for order: " + e.getMessage());
        }
    }

    @Override
    public int insert(final Order order) throws DataMapperException {
        assert order != null;
        final String query = "INSERT into Orders (CreationDate, Status, CustomerId) " +
                             "VALUES (?, ?, ?)";
        try (final PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            statement.setDate(1, new Date(order.getDateCreated().getTimeInMillis()));
            statement.setInt(2, order.getStatus().convert());
            statement.setInt(3, order.getCustomer().getId());
            statement.executeUpdate();

            final int id = getId(statement);
            insertCart(order.getCart(), id);
            return id;
        } catch (SQLException e) {
            throw new DataMapperException("Error occurred while inserting an order: " + e.getMessage());
        }
    }

    @Override
    public void delete(int id) throws DataMapperException {
        final String query = "DELETE from Orders where Id=?";
        try (final PreparedStatement statement = connection.prepareStatement(query)) {
            deleteEntries(id);
            statement.setInt(1, id);

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DataMapperException("Error occurred while deleting an order: " + e.getMessage());
        }
    }

    @Override
    public void update(final Order order) throws DataMapperException {
        throw new DataMapperException("Orders should never be updated!");
    }

    private Cart getCart(int id) throws DataMapperException {
        final String query = "SELECT * from OrderEntries " +
                             "INNER JOIN Cart on " +
                             "Cart.OrderId=? AND Cart.OrderEntryId=OrderEntries.Id";
        try (final PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            final ResultSet rs = statement.executeQuery();

            final Mapper<Book> bookMapper = new BookMapper(connection);
            final Cart result = new Cart();
            while (rs.next()) {
                final int bookId = rs.getInt("BookId");
                final int amount = rs.getInt("Amount");
                final int entryId = rs.getInt("Id");
                final Book book = bookMapper.find(bookId);
                assert book != null;
                result.put(new OrderEntry(entryId, book, amount));
            }

            return result;
        } catch (SQLException e) {
            throw new DataMapperException("Error occurred while acquiring the cart: " + e.getMessage());
        }
    }

    private void insertCart(final Cart cart, int id) throws DataMapperException {
        final String query = "INSERT INTO Cart VALUES (?, ?)";
        try (final PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);

            final Mapper<OrderEntry> entryMapper = new OrderEntryMapper(connection);
            for (final OrderEntry entry : cart.values()) {
                final int entryId = entryMapper.insert(entry);
                statement.setInt(2, entryId);
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new DataMapperException("Error occurred while inserting the cart: " + e.getMessage());
        }
    }

    private void deleteEntries(int id) throws DataMapperException {
        final String query = "DELETE FROM OrderEntries " +
                             "WHERE Id IN " +
                             "(SELECT OrderEntryId FROM " +
                             "Cart WHERE OrderId=?)";
        try (final PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DataMapperException("Error occurred while deleting the cart: " + e.getMessage());
        }
    }
}
