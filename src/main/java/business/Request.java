/**
 * 
 */
package business;

import java.util.GregorianCalendar;


/**
 * @author alexander
 * 
 */
public class Request {
    protected final long id_;
    protected final GregorianCalendar creationDate_;
    protected final Publisher owner_;

    public Request(long id, GregorianCalendar creationDate, Publisher owner) {
        assert (creationDate != null);
        assert (owner != null);

        id_ = id;
        creationDate_ = creationDate;
        owner_ = owner;
    }

    public long getId() {
        return id_;
    }

    public GregorianCalendar getCreationDate() {
        return creationDate_;
    }

    public Publisher getOwner() {
        return owner_;
    }
}