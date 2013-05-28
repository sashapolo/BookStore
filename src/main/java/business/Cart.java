/**
 * 
 */
package business;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


/**
 * @author alexander
 * 
 */
public class Cart implements Iterable<OrderEntry> {
    private final Map<Book, OrderEntry> entries_;

    public Cart() {
        entries_ = new HashMap<>();
    }
    public Cart(final Iterable<OrderEntry> orderList) {
        entries_ = new HashMap<>();
        for (final OrderEntry entry : orderList) {
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

    public OrderEntry remove(final Book book) {
        assert (book != null);
        return entries_.remove(book);
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

    @Override
    public Iterator<OrderEntry> iterator() {
        return values().iterator();
    }

    public void clear() {
        entries_.clear();
    }
}
