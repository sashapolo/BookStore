/**
 * 
 */
package business;

import java.math.BigDecimal;
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

    public BigDecimal getPrice(final Discount userDiscount) {
        assert (userDiscount != null);

        BigDecimal result = new BigDecimal(0);
        for (final OrderEntry e : entries_.values()) {
            result = result.add(e.getPrice());
        }
        return result.multiply(userDiscount.getInverted());
    }

    public BigDecimal getDisplayedPrice(final Discount userDiscount) {
        return getPrice(userDiscount).setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    public boolean isEmpty() {
        return entries_.isEmpty();
    }
    
    public int size() {
    	return entries_.size();
    }
}
