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

    public void put(OrderEntry entry) {
        assert (entry != null);
        
        OrderEntry tmp = entries_.get(entry.getBook());
        if (tmp != null) {
        	entries_.put(entry.getBook(), entry.add(tmp));
        } else {
        	entries_.put(entry.getBook(), entry);
        }
    }

    public BigDecimal getPrice(Discount userDiscount) {
        assert (userDiscount != null);

        BigDecimal result = new BigDecimal(0);
        for (OrderEntry e : entries_.values()) {
            result = result.add(e.getPrice());
        }
        return result.multiply(userDiscount.getInverted());
    }

    public BigDecimal getDisplayedPrice(Discount userDiscount) {
        return getPrice(userDiscount).setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    public boolean isEmpty() {
        return entries_.isEmpty();
    }
    
    public int size() {
    	return entries_.size();
    }
}
