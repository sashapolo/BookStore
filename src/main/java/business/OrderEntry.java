/**
 * 
 */
package business;


import java.io.Serializable;

/**
 * @author alexander
 * 
 */
public final class OrderEntry implements Serializable {
    private static final long serialVersionUID = -2244053755189602328L;
    private final int id;
    private final int amount;
    private final Book book;

    public OrderEntry(final int id, final Book book, final int amount) {
        assert book != null;
        assert amount > 0;
        
        this.id = id;
        this.amount = amount;
        this.book = book;
    }
    
    public OrderEntry(final Book book, final int amount) {
        this(0, book, amount);
    }
    
    public OrderEntry add(final OrderEntry other) {
    	assert other != null;
    	assert book.equals(other.book) : "Trying to add order entries containing different books";

    	return new OrderEntry(id, book, amount + other.amount);
    }

    public int getId() {
        return id;
    }

    public int getAmount() {
        return amount;
    }

    public Book getBook() {
        return book;
    }

    public double getPrice() {
        return book.getPrice() * amount;
    }
}
