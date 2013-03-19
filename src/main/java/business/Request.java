/**
 * 
 */
package business;

import java.util.GregorianCalendar;


/**
 * @author alexander
 * 
 */
public abstract class Request {
    protected final long id_ = IdDispatcher.INSTANCE.getNewRequestId();
    protected final GregorianCalendar creationDate_ = new GregorianCalendar();
    protected final Publisher owner_;
    protected RequestStatus status_ = RequestStatus.CREATED;

    public enum RequestStatus {
        // TODO add some statuses
        CREATED, APPROVED, DECLINED
    }
    
    public Request(Publisher owner) {
        assert (owner != null);
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
    
    public RequestStatus getStatus() {
    	return status_;
    }
    
    public abstract void approve();
}