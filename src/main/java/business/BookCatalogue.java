/**
 * 
 */
package business;

import java.util.HashMap;
import java.util.Map;


/**
 * @author alexander
 * 
 */
public class BookCatalogue {
    private Map<Isbn, Book> catalogue_;

    public BookCatalogue() {
        catalogue_ = new HashMap<>();
    }

    public Book getBook(Isbn key) {
        return catalogue_.get(key);
    }

    public Book addBook(Book book) {
        return catalogue_.put(book.getIsbn(), book);
    }
}
