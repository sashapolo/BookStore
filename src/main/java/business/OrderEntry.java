/**
 * 
 */
package business;

import java.math.BigDecimal;

/**
 * @author alexander
 * 
 */
public class OrderEntry {
	private int amount_;
	private final Book book_;

	public OrderEntry(Book book, int amount) {
		assert (book != null);
		if (amount <= 0) {
			throw new IllegalArgumentException("Amount must be of positive value");
		}
		amount_ = amount;
		book_ = book;
	}

	public int getAmount() {
		return amount_;
	}

	public Book getBook() {
		return book_;
	}

	public BigDecimal getPrice() {
		return book_.getPrice().multiply(BigDecimal.valueOf(amount_));
	}

	public BigDecimal getDisplayedPrice() {
		return getPrice().setScale(2, BigDecimal.ROUND_HALF_UP);
	}
}
