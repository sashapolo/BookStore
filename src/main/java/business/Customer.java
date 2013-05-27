/**
 * 
 */
package business;


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
                    final String email) {
        super(id, login, password, name, secondName, email);
        personalDiscount_ = new Discount(0);
    }
    
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
    
    public void addOrderEntry(final OrderEntry entry) {
    	currentCart_.put(entry);
    }
}
