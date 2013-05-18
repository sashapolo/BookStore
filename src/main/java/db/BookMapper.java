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
    private final Connection connection_;

    public BookMapper(final Connection connection) {
        assert(connection != null);
        connection_ = connection;
    }

    private Book createBook(final ResultSet rs) throws DataMapperException, SQLException {
        final int id = rs.getInt("Id");
        final String name = rs.getString("Name");
        final String genre = rs.getString("Genre");
        final Isbn isbn = new Isbn13(rs.getString("Isbn"));

        final GregorianCalendar date = new GregorianCalendar();
        date.setTime(rs.getDate("PublicationDate"));

        final double price = rs.getDouble("Price");
        final Discount discount = new Discount(rs.getInt("Discount"));

        final UserMapper userMapper = new UserMapper(connection_);
        final User publisher = userMapper.findById(rs.getInt("PublisherId"));
        if (publisher == null) {
            throw new DataMapperException("Publisher not found");
        }
        if (!(publisher instanceof Publisher)) {
            throw new DataMapperException("Incorrect publisher type");
        }

        final int numSold = rs.getInt("NumSold");

        return new Book.Builder(id, name, genre, (Publisher) publisher, date, isbn, price)
                            .discount(discount)
                            .numSold(numSold)
                            .build();
    }

    public List<Book> findByName(final String bookName) throws DataMapperException {
        assert(bookName != null);

        PreparedStatement statement = null;
        final List<Book> result = new LinkedList<>();
        try {
            final String query = "SELECT * from Books where Name=?";
            statement = connection_.prepareStatement(query);
            statement.setString(1, bookName);
            final ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                result.add(createBook(rs));
            }

            return result;
        } catch (SQLException e) {
            throw new DataMapperException("Error occurred while searching for book", e);
        } finally {
            try {
                if (statement != null) statement.close();
            } catch (SQLException e) {}
        }
    }

    public Book findById(final int id) throws DataMapperException {
        PreparedStatement statement = null;
        try {
            final String query = "SELECT * from Books where Id=?";
            statement = connection_.prepareStatement(query);
            statement.setInt(1, id);
            final ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                return createBook(rs);
            }

            return null;
        } catch (SQLException e) {
            throw new DataMapperException("Error occurred while searching for book", e);
        } finally {
            try {
                if (statement != null) statement.close();
            } catch (SQLException e) {}
        }
    }

    public void insert(final Book book) throws DataMapperException {
        assert (book != null);

        PreparedStatement statement = null;
        try {
            final String query = "INSERT into Books VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            statement = connection_.prepareStatement(query);

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
            try {
                if (statement != null) statement.close();
            } catch (SQLException e) {}
        }
    }

    public void update(final Book book) throws DataMapperException {
        assert (book != null);

        PreparedStatement statement = null;
        try {
            final String query = "UPDATE Books SET " +
                                 "Name=?, Genre=?, Isbn=?, PublicationDate=?, Price=?, Discount=?, " +
                                 "NumSold=?, PublisherId=?" +
                                 "where Id=?";
            statement = connection_.prepareStatement(query);

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
            try {
                if (statement != null) statement.close();
            } catch (SQLException e) {}
        }
    }

    public void delete(final Book book) throws DataMapperException {
        assert (book != null);

        PreparedStatement statement = null;
        try {
            final String query = "DELETE from Books where Id=?";
            statement = connection_.prepareStatement(query);
            statement.setInt(1, book.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DataMapperException("Error occurred while deleting a book", e);
        } finally {
            try {
                if (statement != null) statement.close();
            } catch (SQLException e) {}
        }
    }
}
