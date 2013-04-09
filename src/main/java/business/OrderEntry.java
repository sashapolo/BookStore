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

    public OrderEntry(final Book book, final int amount) {
        assert (book != null);

        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be of positive value");
        }
        amount_ = amount;
        book_ = book;
    }
    
    public OrderEntry add(final OrderEntry other) {
    	assert (other != null);
    	
    	if (!equals(other)) {
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
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof OrderEntry)) return false;

        final OrderEntry that = (OrderEntry) o;
        return book_.equals(that.book_);
    }

    @Override
    public int hashCode() {
        int result = amount_;
        result = 31 * result + book_.hashCode();
        return result;
    }
}
