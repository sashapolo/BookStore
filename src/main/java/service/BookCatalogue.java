/**
 * 
 */
package service;

import business.Book;
import business.BookStore;
import business.Isbn;
import business.Isbn10;
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
        createBook(book, 0);
    }
    
    public static void createBook(final Book book, int amount) throws EntryRedefinitionException {
        assert (book != null);
        final ConnectionManager manager = new DerbyConnectionManager();
        try (Connection connection = manager.getConnection("db")) {
            final BookMapper mapper = new BookMapper(connection);
            final Book test = mapper.find(book.getIsbn());
            if (test != null) throw new EntryRedefinitionException("Book already exists");
            mapper.insert(book, amount);
        } catch (SQLException | DataMapperException e) {
            LOGGER.log(Level.SEVERE, e.getMessage());
            throw new IllegalStateException(e.getMessage());
        }
    }

    public static List<Book> getBooks(final String search) {
        assert (search != null);
        
        final ConnectionManager manager = new DerbyConnectionManager();
        try (Connection connection = manager.getConnection("db")) {
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
            throw new IllegalStateException(e.getMessage());
        }
    }
    
    public static Book getBook(final String isbn) throws WrongFormatException {
        assert (isbn != null);
        
        Isbn parsedIsbn;
        try {
            parsedIsbn = new Isbn10(isbn).toIsbn13();
        } catch (WrongFormatException e) {
            parsedIsbn = new Isbn13(isbn);
        }
        
        final ConnectionManager manager = new DerbyConnectionManager();
        try (Connection connection = manager.getConnection("db")) {
            final BookMapper mapper = new BookMapper(connection);
            return mapper.find(parsedIsbn);
        } catch (SQLException | DataMapperException e) {
            LOGGER.log(Level.SEVERE, e.getMessage());
            throw new IllegalStateException(e.getMessage());
        }
    }
    
    public static int getAmount(final Book book) throws EntryNotFoundException {
        assert (book != null);
        
        final ConnectionManager manager = new DerbyConnectionManager();
        try (Connection connection = manager.getConnection("db")) {
            final BookMapper mapper = new BookMapper(connection);
            int result = mapper.getAmount(book.getId());
            if (result == -1) throw new EntryNotFoundException("Book not found");
            return result;
        } catch (SQLException | DataMapperException e) {
            LOGGER.log(Level.SEVERE, e.getMessage());
            throw new IllegalStateException(e.getMessage());
        }
    }
    
    public static void setAmount(final Book book, int amount) throws EntryNotFoundException {
        assert (book != null);
        
        final ConnectionManager manager = new DerbyConnectionManager();
        try (Connection connection = manager.getConnection("db")) {
            final BookMapper mapper = new BookMapper(connection);
            mapper.setAmount(book.getId(), amount);
        } catch (SQLException | DataMapperException e) {
            LOGGER.log(Level.SEVERE, e.getMessage());
            throw new IllegalStateException(e.getMessage());
        }
    }
    
    public static void deleteBook(final Book book) {
        assert (book != null);
        
        final ConnectionManager manager = new DerbyConnectionManager();
        try (Connection connection = manager.getConnection("db")) {
            final BookMapper mapper = new BookMapper(connection);
            mapper.delete(book.getId());
        } catch (SQLException | DataMapperException e) {
            LOGGER.log(Level.SEVERE, e.getMessage());
            throw new IllegalStateException(e.getMessage());
        }
    }
    
    public static void updateBook(final Book book) {
        assert (book != null);
        
        final ConnectionManager manager = new DerbyConnectionManager();
        try (Connection connection = manager.getConnection("db")) {
            final BookMapper mapper = new BookMapper(connection);
            mapper.update(book);
        } catch (SQLException | DataMapperException e) {
            LOGGER.log(Level.SEVERE, e.getMessage());
            throw new IllegalStateException(e.getMessage());
        }
    }
    
    public static int getNumSold(final Book book) throws EntryNotFoundException {
        assert (book != null);
        
        final ConnectionManager manager = new DerbyConnectionManager();
        try (Connection connection = manager.getConnection("db")) {
            final BookMapper mapper = new BookMapper(connection);
            int result = mapper.getNumSold(book.getId());
            if (result == -1) throw new EntryNotFoundException("Book not found");
            return result;
        } catch (SQLException | DataMapperException e) {
            LOGGER.log(Level.SEVERE, e.getMessage());
            throw new IllegalStateException(e.getMessage());
        }
    }

    private BookCatalogue() {}
}
