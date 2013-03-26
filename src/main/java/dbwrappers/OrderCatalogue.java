/**
 * 
 */
package dbwrappers;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import business.Order;
import business.User;

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
	
	public List<Order> getOrdersByUser(User user) {
		assert (user != null);
		
		List<Order> result = new LinkedList<>();
		for (Order order : orders_.values()) {
			if (order.getOrderer().getId() == user.getId()) {
				result.add(order);
			}
		}
		return result;
	}
}