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
	private int id_;
	private final GregorianCalendar creationDate_;
	private final Publisher owner_;

	public Request(int id, GregorianCalendar creationDate, Publisher owner) {
		assert (id >= 0);
		assert (creationDate != null);
		assert (owner != null);

		id_ = id;
		creationDate_ = creationDate;
		owner_ = owner;
	}

	public int getId() {
		return id_;
	}
}
