package db;

import business.*;

import java.sql.*;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: alexander
 * Date: 5/13/13
 * Time: 7:01 PM
 * To change this template use File | Settings | File Templates.
 */
public class BookMapper {
    private ConnectionManager connectionManager_;

    public BookMapper(ConnectionManager manager) {
        assert(manager != null);
        connectionManager_ = manager;
    }

    private Book createBook(final ResultSet rs) throws DataMapperException, SQLException {
        int id = rs.getInt("Id");
        String name = rs.getString("Name");
        String genre = rs.getString("Genre");
        Isbn isbn = new Isbn13(rs.getString("Isbn"));

        GregorianCalendar date = new GregorianCalendar();
        date.setTime(rs.getDate("PublicationDate"));

        double price = rs.getDouble("Price");
        Discount discount = new Discount(rs.getInt("Discount"));

        UserMapper userMapper = new UserMapper(connectionManager_);
        User publisher = userMapper.findById(rs.getInt("PublisherId"));
        if (publisher == null) {
            throw new DataMapperException("Publisher not found");
        }
        if (!(publisher instanceof Publisher)) {
            throw new DataMapperException("Incorrect publisher type");
        }

        int numSold = rs.getInt("NumSold");

        return new Book.Builder(id, name, genre, (Publisher) publisher, date, isbn, price)
                            .discount(discount)
                            .numSold(numSold)
                            .build();
    }

    public List<Book> findByName(final String bookName) throws DataMapperException {
        assert(bookName != null);

        List<Book> result = new LinkedList<>();
        Connection conn = null;
        try {
            conn = connectionManager_.getConnection();

            String query = "SELECT * from Books where Name=?";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, bookName);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                result.add(createBook(rs));
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

    public Book findById(int id) throws DataMapperException {
        Connection conn = null;
        try {
            conn = connectionManager_.getConnection();

            String query = "SELECT * from Books where Id=?";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                return createBook(rs);
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

    public void insert(final Book book) throws DataMapperException {
        Connection conn = null;
        try {
            conn = connectionManager_.getConnection();

            String query = "INSERT into Books VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = conn.prepareStatement(query);

            statement.setString(1, book.getName());
            statement.setString(2, book.getGenre());
            statement.setString(3, book.getIsbn().toString());
            statement.setDate(4, new Date(book.getPublicationDate().getTimeInMillis()));
            statement.setDouble(5, book.getPrice());
            statement.setInt(6, book.getDiscount().integerValue());
            statement.setInt(7, book.getNumSold());
            statement.setInt(8, book.getPublisher().getId());

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DataMapperException("Error occurred while inserting a book", e);
        } finally {
            if (conn != null) {
                try {
                    connectionManager_.closeConnection(conn);
                } catch (SQLException e2) {}
            }
        }
    }

    public void update(final Book book) throws DataMapperException {
        Connection conn = null;
        try {
            conn = connectionManager_.getConnection();

            String query = "UPDATE Books SET " +
                           "Name=?, Genre=?, Isbn=?, PublicationDate=?, Price=?, Discount=?, " +
                           "NumSold=?, PublisherId=?" +
                           "where Id=?";
            PreparedStatement statement = conn.prepareStatement(query);

            statement.setString(1, book.getName());
            statement.setString(2, book.getGenre());
            statement.setString(3, book.getIsbn().toString());
            statement.setDate(4, new Date(book.getPublicationDate().getTimeInMillis()));
            statement.setDouble(5, book.getPrice());
            statement.setInt(6, book.getDiscount().integerValue());
            statement.setInt(7, book.getNumSold());
            statement.setInt(8, book.getPublisher().getId());

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DataMapperException("Error occurred while updating a book", e);
        } finally {
            if (conn != null) {
                try {
                    connectionManager_.closeConnection(conn);
                } catch (SQLException e2) {}
            }
        }
    }

    public void delete(final Book book) throws DataMapperException {
        Connection conn = null;
        try {
            conn = connectionManager_.getConnection();

            String query = "DELETE from Books where Id=?";
            PreparedStatement statement = conn.prepareStatement(query);

            statement.setInt(1, book.getId());

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DataMapperException("Error occurred while deleting a book", e);
        } finally {
            if (conn != null) {
                try {
                    connectionManager_.closeConnection(conn);
                } catch (SQLException e2) {}
            }
        }
    }
}
