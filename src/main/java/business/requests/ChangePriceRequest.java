/**
 * 
 */
package business.requests;

import business.Book;
import business.Isbn;
import business.Publisher;
import dbwrappers.BookCatalogue;


/**
 * @author alexander
 *
 */
public class ChangePriceRequest extends Request {
    private final Isbn isbn_;
    private final double price_;

    public ChangePriceRequest(final Publisher owner, final Isbn isbn, final double price) {
        super(owner);
        assert (isbn != null);
        if (!BookCatalogue.INSTANCE.contains(isbn)) {
        	throw new IllegalArgumentException("Trying to change price of a nonexisting book");
        }
        isbn_ = isbn;
        price_ = price;
    }
    
    public void approve() {
    	assert (BookCatalogue.INSTANCE.contains(isbn_));
    	final Book book = BookCatalogue.INSTANCE.getBook(isbn_);
    	book.setPrice(price_);
    	status_ = RequestStatus.APPROVED;
    }
}
