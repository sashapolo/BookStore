/**
 * 
 */
package dbwrappers;

import java.util.HashMap;
import java.util.Map;

import business.Order;

/**
 * @author alexander
 *
 */
public enum OrderCatalogue {
	INSTANCE;
	
	private Map<Long, Order> orders_ = new HashMap<>();
	
	public void addOrder(Order order) {
		assert (order != null);
		
		orders_.put(order.getId(), order);
	}
}
