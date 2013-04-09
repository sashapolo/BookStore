/**
 * 
 */
package business.requests;

import business.Book;
import business.Publisher;
import dbwrappers.BookCatalogue;

/**
 * @author alexander
 *
 */
public class NewBookRequest extends Request {
    private final Book book_;
    
    public NewBookRequest(final Publisher owner, final Book book) {
        super(owner);
        assert (book != null);
        if (BookCatalogue.INSTANCE.contains(book.getIsbn())) {
        	throw new IllegalArgumentException("Trying to add a book that already exists");
        }
        book_ = book;
    }
    
    @Override
    public void approve() {
    	BookCatalogue.INSTANCE.addBook(book_);
    	status_ = RequestStatus.APPROVED;
    }
}
