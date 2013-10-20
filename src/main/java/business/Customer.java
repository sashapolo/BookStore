/**
 * 
 */
package business;


/**
 * @author alexander
 * 
 */
public final class Customer extends User {
    private final Discount personalDiscount_;
    private final Cart currentCart_ = new Cart();

    public Customer(final int id,
                    final String login,
                    final int password,
                    final Credentials credentials) {
        super(id, login, password, credentials);
        personalDiscount_ = new Discount(0);
    }
    
    public Customer(final int id,
                    final String login,
                    final int password,
                    final Credentials credentials,
                    final int discount) {
        super(id, login, password, credentials);
        personalDiscount_ = new Discount(discount);
    }

    public Discount getPersonalDiscount() {
        return personalDiscount_;
    }
    
    public void addOrderEntry(final OrderEntry entry) {
        assert (entry != null);
    	currentCart_.put(entry);
    }
    
    public void deleteOrderEntry(final OrderEntry entry) {
        assert (entry != null);
        currentCart_.remove(entry.getBook());
    }
    
    public double getCartPrice() {
        return currentCart_.getPrice(personalDiscount_);
    }
    
    public Cart getCart() {
        return currentCart_;
    }
}
