/**
 * 
 */
package business;

import dbwrappers.OrderCatalogue;

import java.util.List;


/**
 * @author alexander
 * 
 */
public class Customer extends User {
    private final Discount personalDiscount_;
    private final Cart currentCart_ = null;

    public Customer(final int id,
                    final String login,
                    final int password,
                    final String name,
                    final String secondName,
                    final String email,
                    final int discount) {
        super(id, login, password, name, secondName, email);
        personalDiscount_ = new Discount(discount);
    }

    public Discount getPersonalDiscount() {
        return personalDiscount_;
    }
    
    public List<Order> getOrders() {
    	return OrderCatalogue.INSTANCE.getOrdersByUser(this);
    }
    
    public void addOrderEntry(final OrderEntry entry) {
    	currentCart_.put(entry);
    }
    
    //TODO shouldn't this stuff be in the service layer?
//    public List<Book> findBook(String search) {
//        return BookCatalogue.INSTANCE.findBook(search);
//    }
//    
//    public OrderEntry buyBook(Book book, int amount) {
//    	assert (book != null);
//    	
//    	if (!BookCatalogue.INSTANCE.containsValue(book)) {
//    		throw new IllegalArgumentException("Book not found");
//    	}
//    	OrderEntry result = new OrderEntry(book, amount);
//    	if (currentCart_ == null) {
//    		currentCart_ = new Cart();
//    	}
//    	currentCart_.add(result);
//    	return result;
//    }
//    
//    public void placeOrder() {
//    	if (currentCart_ == null || currentCart_.isEmpty()) {
//    		throw new IllegalArgumentException("Trying to place an order with empty cart");
//    	}
//    	Order order = new Order(currentCart_, this);
//    	orders_.put(order.getId(), order);
//    	OrderCatalogue.INSTANCE.addOrder(order);
//    	currentCart_ = null;
//    }
}
