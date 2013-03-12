/**
 * 
 */
package business;

import java.util.GregorianCalendar;

/**
 * @author alexander
 *
 */
public class ChangePriceRequest extends Request {
    private final Book book_;
    
    /**
     * @param id
     * @param creationDate
     * @param owner
     */
    public ChangePriceRequest(long id, GregorianCalendar creationDate, Publisher owner, Book book) {
        super(id, creationDate, owner);
        assert (book != null);
        book_ = book;
    }
}
