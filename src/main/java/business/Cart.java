/**
 * 
 */
package business;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;


/**
 * @author alexander
 * 
 */
public class Cart {
    private final List<OrderEntry> entries_;

    public Cart() {
        entries_ = new LinkedList<>();
    }

    public boolean add(OrderEntry entry) {
        assert (entry != null);
        return entries_.add(entry);
    }

    public BigDecimal getPrice(Discount userDiscount) {
        assert (userDiscount != null);

        BigDecimal result = new BigDecimal(0);
        for (OrderEntry e : entries_) {
            result = result.add(e.getPrice());
        }
        return result.multiply(userDiscount.getInverted());
    }

    public BigDecimal getDisplayedPrice(Discount userDiscount) {
        return getPrice(userDiscount).setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    /**
     * @return
     * @see java.util.List#isEmpty()
     */
    public boolean isEmpty() {
        return entries_.isEmpty();
    }

    /**
     * @return
     * @see java.util.List#iterator()
     */
    public Iterator<OrderEntry> iterator() {
        return entries_.iterator();
    }

    /**
     * @return
     * @see java.util.List#size()
     */
    public int size() {
        return entries_.size();
    }

    /**
     * 
     * @see java.util.List#clear()
     */
    public void clear() {
        entries_.clear();
    }

}
