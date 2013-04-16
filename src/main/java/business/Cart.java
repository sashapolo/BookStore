/**
 * 
 */
package business;

import java.util.HashMap;
import java.util.Map;


/**
 * @author alexander
 * 
 */
public class Cart {
    private final Map<Book, OrderEntry> entries_ = new HashMap<>();

    public void put(final OrderEntry entry) {
        assert (entry != null);

        final Book book = entry.getBook();
        final OrderEntry tmp = entries_.get(book);
        if (tmp != null) {
        	entries_.put(book, entry.add(tmp));
        } else {
        	entries_.put(book, entry);
        }
    }

    public double getPrice(final Discount userDiscount) {
        assert (userDiscount != null);

        double result = 0;
        for (final OrderEntry e : entries_.values()) {
            result += e.getPrice();
        }
        return result * userDiscount.getInvertedValue();
    }

    public boolean isEmpty() {
        return entries_.isEmpty();
    }
    
    public int size() {
    	return entries_.size();
    }
}
