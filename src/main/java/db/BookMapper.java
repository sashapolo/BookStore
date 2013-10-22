package db;

import business.Book;
import business.Discount;
import business.Isbn;
import business.Publisher;

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
public final class BookMapper extends Mapper<Book>{

    public BookMapper(final Connection connection) {
        super(connection);
    }

    public List<Book> find(final String bookName) throws DataMapperException {
        assert(bookName != null);

        final List<Book> result = new LinkedList<>();
        final String query = "SELECT * from Books where Name=?";

        try (final PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, bookName);

            final ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                result.add(createBook(rs));
            }
            return result;
        } catch (SQLException e) {
            throw new DataMapperException("Error occurred while searching for book: " + e.getMessage());
        }
    }
    
    public Book find(final Isbn isbn) throws DataMapperException {
        assert(isbn != null);

        final String query = "SELECT * from Books where Isbn=?";
        try (final PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, isbn.toString());

            final ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                return createBook(rs);
            }
            return null;
        } catch (SQLException e) {
            throw new DataMapperException("Error occurred while searching for book: " + e.getMessage());
        }
    }

    @Override
    public Book find(final int id) throws DataMapperException {
        final String query = "SELECT * from Books where Id=?";
        try (final PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);

            final ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                return createBook(rs);
            }
            return null;
        } catch (SQLException e) {
            throw new DataMapperException("Error occurred while searching for book: " + e.getMessage());
        }
    }

    @Override
    public int insert(final Book book) throws DataMapperException {
        return insert(book, 0);
    }
    
    public int insert(final Book book, int amount) throws DataMapperException {
        assert (book != null);

        int id;
        final String query = "INSERT into Books(Name, Author, Genre, Isbn, PublicationDate, Price, Discount, PublisherId)" +
                             "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (final PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, book.getName());
            statement.setString(2, book.getAuthor());
            statement.setString(3, book.getGenre());
            statement.setString(4, book.getIsbn().toString());
            statement.setDate(5, new Date(book.getPublicationDate().getTimeInMillis()));
            statement.setDouble(6, book.getPrice());
            statement.setInt(7, book.getDiscount().integerValue());
            statement.setInt(8, book.getPublisher().getId());
            statement.executeUpdate();

            id = getId(statement);
        } catch (SQLException e) {
            throw new DataMapperException("Error occurred while inserting a book: " + e.getMessage());
        }
            
        final String stockQuery = "INSERT into Stock(Id, Amount, NumSold) VALUES (?, ?, 0)";
        try (final PreparedStatement statement = connection.prepareStatement(stockQuery)) {
            statement.setInt(1, id);
            statement.setInt(2, amount);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DataMapperException("Error occurred while inserting a book: " + e.getMessage());
        }

        return id;
    }
    
    @Override
    public void update(final Book book) throws DataMapperException {
        assert (book != null);

        final String query = "UPDATE Books SET " +
                             "Name=?, Author=?, Genre=?, Isbn=?, PublicationDate=?, Price=?, Discount=?, PublisherId=?" +
                             "where Id=?";
        try (final PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, book.getName());
            statement.setString(2, book.getAuthor());
            statement.setString(3, book.getGenre());
            statement.setString(4, book.getIsbn().toString());
            statement.setDate(5, new Date(book.getPublicationDate().getTimeInMillis()));
            statement.setDouble(6, book.getPrice());
            statement.setInt(7, book.getDiscount().integerValue());
            statement.setInt(8, book.getPublisher().getId());
            statement.setInt(9, book.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DataMapperException("Error occurred while updating a book: " + e.getMessage());
        }
    }

    @Override
    public void delete(final int id) throws DataMapperException {
        final String query = "DELETE from Books where Id=?";
        try (final PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DataMapperException("Error occurred while deleting a book: " + e.getMessage());
        }

        final String stockQuery = "DELETE from Stock where Id=?";
        try (final PreparedStatement statement = connection.prepareStatement(stockQuery)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DataMapperException("Error occurred while deleting a book: " + e.getMessage());
        }
    }
    
    public int getAmount(final int id) throws DataMapperException {
        final String query = "SELECT * from Stock where Id=?";
        try (final PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            statement.executeQuery();
            
            final ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                return rs.getInt("Amount");
            }

            return -1;
        } catch (SQLException e) {
            throw new DataMapperException("Error: " + e.getMessage());
        }
    }
    
    public void setAmount(int id, int amount) throws DataMapperException {
        final String query = "UPDATE Stock SET Amount=? where Id=?";
        try (final PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, amount);
            statement.setInt(2, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DataMapperException("Error: " + e.getMessage());
        }
    }

    public void buy(final int id, final int amount) throws DataMapperException {
        final String query = "SELECT * from Stock where Id=?";
        try (final PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            statement.executeQuery();
            
            final ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                int numSold = rs.getInt("NumSold");
                int numAvailable = rs.getInt("Amount");
                assert (numAvailable >= amount);

                final String query2 = "UPDATE Stock SET NumSold=?, Amount=? where Id=?";
                try (final PreparedStatement statement2 = connection.prepareStatement(query2)) {
                    statement2.setInt(1, numSold + amount);
                    statement2.setInt(2, numAvailable - amount);
                    statement2.setInt(3, id);
                    statement2.executeUpdate();
                } catch (SQLException e) {
                    throw new DataMapperException("Error: " + e.getMessage());
                }

                return;
            }
            throw new DataMapperException("Book not found");
        } catch (SQLException e) {
            throw new DataMapperException("Error: " + e.getMessage());
        }
    }
    
    public int getNumSold(int id) throws DataMapperException {
        final String query = "SELECT * from Stock where Id=?";
        try (final PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            statement.executeQuery();
            
            final ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                return rs.getInt("NumSold");
            }
            return -1;
        } catch (SQLException e) {
            throw new DataMapperException("Error: " + e.getMessage());
        }
    }
    
    private Book createBook(final ResultSet rs) throws DataMapperException, SQLException {
        final int id = rs.getInt("Id");
        final String name = rs.getString("Name");
        final String author = rs.getString("Author");
        final String genre = rs.getString("Genre");
        final Isbn isbn = new Isbn(rs.getString("Isbn"));

        final GregorianCalendar date = new GregorianCalendar();
        date.setTime(rs.getDate("PublicationDate"));

        final double price = rs.getDouble("Price");
        final Discount discount = new Discount(rs.getInt("Discount"));

        final Mapper<Publisher> pubMapper = new PublisherMapper(connection);
        final Publisher publisher = pubMapper.find(rs.getInt("PublisherId"));
        if (publisher == null) {
            throw new DataMapperException("Publisher not found");
        }

        return new Book.Builder(name, author, genre, publisher, date, isbn, price)
                            .id(id)
                            .discount(discount)
                            .build();
    }
}
