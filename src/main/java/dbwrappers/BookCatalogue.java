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
    	assert (key != null); 
        return catalogue_.get(key);
    }

    public Book addBook(Book book) {
    	Stock.INSTANCE.createEntry(book.getIsbn(), 0);
        return catalogue_.put(book.getIsbn(), book);
    }
    
	public void clear() {
		catalogue_.clear();
	}

	public List<Book> getBooks(String search) {
        assert (search != null);
        
        List<Book> result = new LinkedList<>();
        // first trying to find the book by ISBN
        try {
            Book tmp = getBook(new Isbn13(search));
            if (tmp != null) {
                result.add(tmp);
            }
        } catch (IllegalArgumentException e) {
        // if the search string is not valid ISBN 
        // then we try to match it with the book's name, genre, etc.
            for (Book book : catalogue_.values()) {
                if (book.matchesString(search)) {
                	result.add(book);
                }
            }
        }
        return result;
    }
    
	public boolean contains(Isbn isbn) {
		return catalogue_.containsKey(isbn);
	}
	
	public int size() {
		return catalogue_.size();
	}
}
