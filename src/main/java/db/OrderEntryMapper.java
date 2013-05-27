package db;

import business.Book;
import business.OrderEntry;
import java.sql.*;

/**
 * Created with IntelliJ IDEA.
 * User: alexander
 * Date: 5/14/13
 * Time: 4:49 PM
 * To change this template use File | Settings | File Templates.
 */
public class OrderEntryMapper extends Mapper<OrderEntry> {

    public OrderEntryMapper(final Connection connection) {
        super(connection);
    }

    @Override
    public OrderEntry find(final int id) throws DataMapperException {
        PreparedStatement statement = null;
        try {
            final String query = "SELECT * from OrderEntries where Id=?";
            statement = connection_.prepareStatement(query);
            statement.setInt(1, id);

            final ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                final int amount = rs.getInt("Amount");
                final Mapper<Book> bookMapper = new BookMapper(connection_);
                final Book book = bookMapper.find(rs.getInt("BookId"));
                if (book == null) {
                    throw new DataMapperException("Book not found");
                }
                return new OrderEntry(id, book, amount);
            }

            return null;
        } catch (SQLException e) {
            throw new DataMapperException("Error occurred while searching for entry: " + e.getMessage());
        } finally {
            try {
                if (statement != null) statement.close();
            } catch (SQLException e) {}
        }
    }

    @Override
    public int insert(final OrderEntry entry) throws DataMapperException {
        assert (entry != null);

        PreparedStatement statement = null;
        try {
            final String query = "INSERT into OrderEntries (BookId, Amount) VALUES (?, ?)";
            statement = connection_.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, entry.getBook().getId());
            statement.setInt(2, entry.getAmount());
            statement.executeUpdate();
            return getId(statement);
        } catch (SQLException e) {
            throw new DataMapperException("Error occurred while inserting an entry: " + e.getMessage());
        } finally {
            try {
                if (statement != null) statement.close();
            } catch (SQLException e) {}
        }
    }

    @Override
    public void update(final OrderEntry entry) throws DataMapperException {
        assert (entry != null);
        throw new DataMapperException("Orders should never be updated!");
    }

    @Override
    public void delete(final int id) throws DataMapperException {
        PreparedStatement statement = null;
        try {
            final String query = "DELETE from OrderEntries where Id=?";
            statement = connection_.prepareStatement(query);
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DataMapperException("Error occurred while deleting an entry: " + e.getMessage());
        } finally {
            try {
                if (statement != null) statement.close();
            } catch (SQLException e) {}
        }
    }
}
