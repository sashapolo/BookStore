/**
 * 
 */
package business.requests;

import dbwrappers.Stock;
import business.Isbn;
import business.Publisher;

/**
 * @author alexander
 *
 */
public class AddBooksRequest extends Request {
	private final int amount_;
	private final Isbn isbn_;
	
	public AddBooksRequest(Publisher owner, Isbn isbn, int amount) {
		super(owner);
		assert (isbn != null);
		assert (amount > 0);
		
		if (!Stock.INSTANCE.contains(isbn)) {
			throw new IllegalArgumentException("Trying to increase amount of nonexistent book");
		}
		amount_ = amount;
		isbn_ = isbn;
	}
	
	@Override
	public void approve() {
		Stock.INSTANCE.add(isbn_, amount_);	
		status_ = RequestStatus.APPROVED;
	}

}
