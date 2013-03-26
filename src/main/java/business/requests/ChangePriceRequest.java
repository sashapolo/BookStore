/**
 * 
 */
package business.requests;

import java.math.BigDecimal;

import business.Book;
import business.Isbn13;
import business.Publisher;

import dbwrappers.BookCatalogue;


/**
 * @author alexander
 *
 */
public class ChangePriceRequest extends Request {
    private final Isbn13 isbn_;
    private final BigDecimal price_;

    public ChangePriceRequest(Publisher owner, Isbn13 isbn, BigDecimal price) {
        super(owner);
        assert (isbn != null);
        assert (price != null);
        if (!BookCatalogue.INSTANCE.contains(isbn)) {
        	throw new IllegalArgumentException("Trying to change price of a nonexisting book");
        }
        isbn_ = isbn;
        price_ = price;
    }
    
    public void approve() {
    	assert (BookCatalogue.INSTANCE.contains(isbn_));
    	Book book = BookCatalogue.INSTANCE.getBook(isbn_);
    	book.setPrice(price_);
    	status_ = RequestStatus.APPROVED;
    }
}
