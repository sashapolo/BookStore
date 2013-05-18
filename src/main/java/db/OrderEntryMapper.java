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
public class OrderEntryMapper {
    private Connection connection_;

    public OrderEntryMapper(final Connection connection) {
        assert(connection != null);
        connection_ = connection;
    }

    public OrderEntry findById(final int id) throws DataMapperException {
        PreparedStatement statement = null;
        try {
            final String query = "SELECT * from OrderEntries where Id=?";
            statement = connection_.prepareStatement(query);
            statement.setInt(1, id);
            final ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                final int amount = rs.getInt("Amount");

                final BookMapper bookMapper = new BookMapper(connection_);
                final Book book = bookMapper.findById(rs.getInt("BookId"));
                if (book == null) {
                    throw new DataMapperException("Book not found");
                }

                return new OrderEntry(id, book, amount);
            }

            return null;
        } catch (SQLException e) {
            throw new DataMapperException("Error occurred while searching for entry", e);
        } finally {
            try {
                if (statement != null) statement.close();
            } catch (SQLException e) {}
        }
    }

    public int insert(final OrderEntry entry) throws DataMapperException {
        assert (entry != null);

        PreparedStatement statement = null;
        ResultSet keys = null;
        try {
            final String query = "INSERT into OrderEntries VALUES (?, ?)";
            statement = connection_.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

            statement.setInt(1, entry.getBook().getId());
            statement.setInt(2, entry.getAmount());

            statement.executeUpdate();

            keys = statement.getGeneratedKeys();
            if (keys.next()) {
                return keys.getInt(1);
            }
            throw new DataMapperException("Error occurred while retrieving primary key");
        } catch (SQLException e) {
            throw new DataMapperException("Error occurred while inserting an entry", e);
        } finally {
            try {
                if (statement != null) statement.close();
                if (keys != null) keys.close();
            } catch (SQLException e) {}
        }
    }

    public void delete(final OrderEntry entry) throws DataMapperException {
        assert (entry != null);

        PreparedStatement statement = null;
        try {
            final String query = "DELETE from OrderEntries where Id=?";
            statement = connection_.prepareStatement(query);
            statement.setInt(1, entry.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DataMapperException("Error occurred while deleting an entry", e);
        } finally {
            try {
                if (statement != null) statement.close();
            } catch (SQLException e) {}
        }
    }
}
