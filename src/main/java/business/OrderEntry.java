/**
 * 
 */
package business;


/**
 * @author alexander
 * 
 */
public class OrderEntry {
    private final int id_;
    private final int amount_;
    private final Book book_;

    public OrderEntry(final int id, final Book book, final int amount) {
        assert (book != null);

        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be of positive value");
        }
        id_ = id;
        amount_ = amount;
        book_ = book;
    }
    
    public OrderEntry add(final OrderEntry other) {
    	assert (other != null);
    	
    	if (!equals(other)) {
    		throw new IllegalArgumentException("Trying to add order entries containing different books");
    	}
    	return new OrderEntry(id_, book_, amount_ + other.amount_);
    }

    public int getId() {
        return id_;
    }

    public int getAmount() {
        return amount_;
    }

    public Book getBook() {
        return book_;
    }

    public double getPrice() {
        return book_.getPrice() * amount_;
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
