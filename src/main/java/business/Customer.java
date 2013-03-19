/**
 * 
 */
package business;

import java.util.List;

import dbwrappers.OrderCatalogue;


/**
 * @author alexander
 * 
 */
public class Customer extends User {
    private Discount personalDiscount_;
    private Cart currentCart_ = null;

    public Customer(String login, String password, String name, String secondName, String email, int discount) {
        super(login, password, name, secondName, email);
        personalDiscount_ = new Discount(discount);
    }

    public Discount getPersonalDiscount() {
        return personalDiscount_;
    }
    
    public List<Order> getOrders() {
    	return OrderCatalogue.INSTANCE.getOrdersByUser(this);
    }
    
    public void addOrderEntry(OrderEntry entry) {
    	currentCart_.put(entry);
    }
    
    //FIXME shouldn't this stuff be in the service layer?
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
