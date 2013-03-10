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
	private Map<Isbn13, Book> catalogue_;

	public BookCatalogue() {
		catalogue_ = new HashMap<>();
	}

	public Book get(Object key) {
		assert (key != null);
		return catalogue_.get(key);
	}

	public Book put(Book value) {
		return catalogue_.put(value.getIsbn(), value);
	}
}
