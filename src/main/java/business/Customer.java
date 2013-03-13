/**
 * 
 */
package business;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dbwrappers.BookCatalogue;


/**
 * @author alexander
 * 
 */
public class Customer extends User {
    private Discount personalDiscount_;
    private final Map<Long, Order> orders_ = new HashMap<>();
    private Cart currentCart_ = null;

    public Customer(long id, String login, String password, String name, String secondName,
            String email, int discount) {
        super(id, login, password, name, secondName, email);
        personalDiscount_ = new Discount(discount);
    }

    public Discount getPersonalDiscount() {
        return personalDiscount_;
    }
    
    public List<Book> findBook(String search) {
        return BookCatalogue.INSTANCE.findBook(search);
    }
    
    public OrderEntry buyBook(Book book, int amount) {
    	assert (book != null);
    	
    	if (!BookCatalogue.INSTANCE.containsValue(book)) {
    		throw new IllegalArgumentException("Book not found");
    	}
    	OrderEntry result = new OrderEntry(book, amount);
    	if (currentCart_ == null) {
    		currentCart_ = new Cart();
    	}
    	currentCart_.add(result);
    	return result;
    }
    
//    public Order placeOrder() {
//    	
//    }
}
