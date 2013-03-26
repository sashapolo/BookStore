/**
 * 
 */
package dbwrappers;

import java.util.HashMap;
import java.util.Map;

import business.Isbn;

/**
 * @author alexander
 *
 */
public enum Stock {
	INSTANCE;
	
	private final Map<Isbn, Integer> stock_ = new HashMap<>();

	public Integer get(Isbn isbn) {
		return stock_.get(isbn);
	}

	public Integer createEntry(Isbn isbn, int amount) {
		assert (!stock_.containsKey(isbn));
		assert (amount >= 0);
		return stock_.put(isbn, amount);
	}
	
	public void clear() {
		stock_.clear();
	}

	public void add(Isbn isbn, int amount) {
		assert (stock_.containsKey(isbn));
		assert (amount > 0);
		stock_.put(isbn, stock_.get(isbn) + amount);
	}
	
	public void buy(Isbn isbn, int amount) {
		assert (amount > 0);
		assert (stock_.containsKey(isbn));
		if (stock_.get(isbn) < amount) {
			throw new IllegalArgumentException("Not enough goods in the stock");
		}
		stock_.put(isbn, stock_.get(isbn) - amount);
	}

	public boolean contains(Isbn isbn) {
		return stock_.containsKey(isbn);
	}
}
