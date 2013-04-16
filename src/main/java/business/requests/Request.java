/**
 * 
 */
package business.requests;

import java.util.GregorianCalendar;

import business.Publisher;


/**
 * @author alexander
 * 
 */
public abstract class Request {
    protected final GregorianCalendar creationDate_ = new GregorianCalendar();
    protected final Publisher owner_;
    protected RequestStatus status_ = RequestStatus.CREATED;

    public enum RequestStatus {
        // TODO add some statuses
        CREATED, APPROVED, DECLINED
    }
    
    protected Request(final Publisher owner) {
        assert (owner != null);
        owner_ = owner;
    }

    public GregorianCalendar getCreationDate() {
        return (GregorianCalendar) creationDate_.clone();
    }

    public Publisher getOwner() {
        return owner_;
    }
    
    public RequestStatus getStatus() {
    	return status_;
    }
    
    public abstract void approve();
}
