/**
 * 
 */
package business;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author alexander
 * 
 */
public class Cart {
    private final Map<Book, OrderEntry> entries_;

    public Cart() {
        entries_ = new HashMap<>();
    }
    public Cart(final List<OrderEntry> orderList) {
        entries_ = new HashMap<>();
        for (OrderEntry entry : orderList) {
            entries_.put(entry.getBook(), entry);
        }
    }

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

    public Collection<OrderEntry> values() {
        return entries_.values();
    }
}
