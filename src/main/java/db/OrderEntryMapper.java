package db;

import business.Book;
import business.OrderEntry;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created with IntelliJ IDEA.
 * User: alexander
 * Date: 5/14/13
 * Time: 4:49 PM
 * To change this template use File | Settings | File Templates.
 */
public class OrderEntryMapper {
    private ConnectionManager connectionManager_;

    public OrderEntryMapper(ConnectionManager manager) {
        assert(manager != null);
        connectionManager_ = manager;
    }

    public OrderEntry findById(int id) throws DataMapperException {
        Connection conn = null;
        try {
            conn = connectionManager_.getConnection();

            String query = "SELECT * from OrderEntries where Id=?";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                int amount = rs.getInt("Amount");

                BookMapper bookMapper = new BookMapper(connectionManager_);
                Book book = bookMapper.findById(rs.getInt("BookId"));
                if (book == null) {
                    throw new DataMapperException("Book not found");
                }

                return new OrderEntry(id, book, amount);
            }

            return null;
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

    public int insert(final OrderEntry entry) throws DataMapperException {
        assert (entry != null);

        Connection conn = null;
        try {
            conn = connectionManager_.getConnection();

            String query = "INSERT into OrderEntries VALUES (?, ?)";
            PreparedStatement statement = conn.prepareStatement(query);

            statement.setInt(1, entry.getBook().getId());
            statement.setInt(2, entry.getAmount());

            statement.executeUpdate();

            ResultSet keys = statement.getGeneratedKeys();
            if (keys.next()) {
                return keys.getInt(1);
            }
            throw new DataMapperException("Error occurred while retrieving primary key");
        } catch (SQLException e) {
            throw new DataMapperException("Error occurred while inserting an entry", e);
        } finally {
            if (conn != null) {
                try {
                    connectionManager_.closeConnection(conn);
                } catch (SQLException e2) {}
            }
        }
    }

    public void delete(final OrderEntry entry) throws DataMapperException {
        assert (entry != null);

        Connection conn = null;
        try {
            conn = connectionManager_.getConnection();

            String query = "DELETE from OrderEntries where Id=?";
            PreparedStatement statement = conn.prepareStatement(query);

            statement.setInt(1, entry.getId());

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DataMapperException("Error occurred while deleting an entry", e);
        } finally {
            if (conn != null) {
                try {
                    connectionManager_.closeConnection(conn);
                } catch (SQLException e2) {}
            }
        }
    }
}
