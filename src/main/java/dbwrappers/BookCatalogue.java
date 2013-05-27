/**
 * 
 */
package dbwrappers;

import business.Book;
import business.BookStore;
import business.Isbn;
import business.Isbn13;
import business.WrongFormatException;
import db.BookMapper;
import db.ConnectionManager;
import db.DataMapperException;
import db.DerbyConnectionManager;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * @author alexander
 * 
 */
public final class BookCatalogue {
    private static final Logger LOGGER = Logger.getLogger(BookStore.class.getName());

    public static void createBook(final Book book) throws EntryRedefinitionException {
    	Stock.INSTANCE.createEntry(book.getIsbn(), 0);
        final ConnectionManager manager = new DerbyConnectionManager();
        Connection connection = null;
        try {
            connection = manager.getConnection("db");
            final BookMapper mapper = new BookMapper(connection);
            final Book test = mapper.find(book.getIsbn());
            if (test != null) throw new EntryRedefinitionException("Book already exists");
            mapper.insert(book);
        } catch (SQLException | DataMapperException e) {
            LOGGER.log(Level.SEVERE, e.getMessage());
            throw new IllegalStateException("something is very wrong :(", e);
        } finally {
            try {
                if (connection != null) manager.closeConnection(connection);
            } catch (SQLException e) {}
        }
    }

    public static List<Book> getBooks(String search) {
        assert (search != null);
        
        final ConnectionManager manager = new DerbyConnectionManager();
        Connection connection = null;
        try {
            connection = manager.getConnection("db");
            final BookMapper mapper = new BookMapper(connection);
            
            // first trying to find the book by ISBN
            try {
                final Isbn isbn = new Isbn13(search);
                final List<Book> result = new LinkedList<>();
                final Book book = mapper.find(isbn);
                if (book != null) result.add(book);
                return result;
            } catch (WrongFormatException e) {}
            
            return mapper.find(search);
        } catch (SQLException | DataMapperException e) {
            LOGGER.log(Level.SEVERE, e.getMessage());
            throw new IllegalStateException("something is very wrong :(", e);
        } finally {
            try {
                if (connection != null) manager.closeConnection(connection);
            } catch (SQLException e) {}
        }
    }
}
