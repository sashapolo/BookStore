/**
 * 
 */
package edu.business;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


/**
 * @author alexander
 * 
 */
public final class Cart implements Iterable<OrderEntry> {
    private final Map<Book, OrderEntry> entries;

    public Cart() {
        entries = new HashMap<>();
    }
    public Cart(final int size) {
        entries = new HashMap<>(size);
    }

    public void put(final OrderEntry entry) {
        assert entry != null;

        final Book book = entry.getBook();
        final OrderEntry tmp = entries.get(book);
        if (tmp != null) {
        	entries.put(book, entry.add(tmp));
        } else {
        	entries.put(book, entry);
        }
    }

    public void remove(final Book book) {
        assert book != null;
        entries.remove(book);
    }

    public double getPrice(final Discount userDiscount) {
        assert userDiscount != null;

        double result = 0.0;
        for (final OrderEntry e : entries.values()) {
            result += e.getPrice();
        }
        return result * userDiscount.invertedValue();
    }

    public boolean isEmpty() {
        return entries.isEmpty();
    }
    
    public int size() {
    	return entries.size();
    }

    public Collection<OrderEntry> values() {
        return entries.values();
    }

    @Override
    public Iterator<OrderEntry> iterator() {
        return values().iterator();
    }

    public void clear() {
        entries.clear();
    }
}
