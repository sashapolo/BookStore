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
        assert (amount > 0);
        
        id_ = id;
        amount_ = amount;
        book_ = book;
    }
    
    public OrderEntry add(final OrderEntry other) {
    	assert (other != null);
    	assert (book_.equals(other.book_)): "Trying to add order entries containing different books";

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
}
