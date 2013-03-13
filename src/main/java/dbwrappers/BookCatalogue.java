/**
 * 
 */
package dbwrappers;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import business.Book;
import business.Isbn;
import business.Isbn13;


/**
 * @author alexander
 * 
 */
public enum BookCatalogue {
    INSTANCE;
    
    private final Map<Isbn, Book> catalogue_ = new HashMap<>();
    
    public Book getBook(Isbn key) {
        return catalogue_.get(key);
    }

    public Book addBook(Book book) {
        return catalogue_.put(book.getIsbn(), book);
    }
    
    public boolean containsValue(Book book) {
		return catalogue_.containsValue(book);
	}

	public List<Book> findBook(String search) {
        assert (search != null);
        
        List<Book> result = new LinkedList<>();
        // first trying to find the book by ISBN
        try {
            Book tmp = catalogue_.get(new Isbn13(search));
            if (tmp != null) {
                result.add(tmp);
            }
        } catch (IllegalArgumentException e) {
        // if the search string is not valid ISBN 
        // then we try to match it with the book's name, genre, etc.
            for (Book book : catalogue_.values()) {
                if (book.getName().contains(search) || book.getGenre().contains(search) || 
                    book.getPublisher().getName().contains(search)) {
                    result.add(book);
                }
            }
        }
        return result;
    }
    
}
