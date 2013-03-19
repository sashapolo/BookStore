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
    private final int amount_;
    private final Book book_;

    public OrderEntry(Book book, int amount) {
        assert (book != null);

        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be of positive value");
        }
        amount_ = amount;
        book_ = book;
    }
    
    public OrderEntry add(OrderEntry other) {
    	assert (other != null);
    	
    	if (!this.equals(other)) {
    		throw new IllegalArgumentException("Trying to add order entries containing different books");
    	}
    	return new OrderEntry(book_, amount_ + other.amount_);
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((book_ == null) ? 0 : book_.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		OrderEntry other = (OrderEntry) obj;
		if (book_ == null) {
			if (other.book_ != null)
				return false;
		} else if (!book_.equals(other.book_))
			return false;
		return true;
	}   
}
