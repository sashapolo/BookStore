/**
 * 
 */
package business;

import dbwrappers.BookCatalogue;

/**
 * @author alexander
 *
 */
public class NewBookRequest extends Request {
    private final Book book_;
    
    public NewBookRequest(Publisher owner, Book book) {
        super(owner);
        assert (book != null);
        book_ = book;
    }
    
    @Override
    public void approve() {
    	BookCatalogue.INSTANCE.addBook(book_);
    	status_ = RequestStatus.APPROVED;
    }
}
