/**
 * 
 */
package business;

import java.math.BigDecimal;
import java.util.GregorianCalendar;


/**
 * @author alexander
 * 
 */
public class Order {
    private final int id_;
    private final GregorianCalendar dateCreated_ = new GregorianCalendar();
    private final Cart cart_;
    private final BigDecimal cartPrice_;
    private OrderStatus status_ = OrderStatus.CREATED;
    private final Customer orderer_;

    public enum OrderStatus {
        // TODO add some statuses
        CREATED
    }
    
    public Order(final int id, final Cart cart, final Customer orderer) {
        assert (cart != null);
        assert (orderer != null);

        id_ = id;
        cart_ = cart;
        cartPrice_ = cart.getPrice(orderer.getPersonalDiscount());
        orderer_ = orderer;
        status_ = OrderStatus.CREATED;
    }

    public int getId() {
        return id_;
    }

    public GregorianCalendar getDateCreated() {
        return dateCreated_;
    }

    public BigDecimal getPrice() {
        return cartPrice_;
    }

    public BigDecimal getDisplayedPrice() {
        return cartPrice_.setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    public OrderStatus getStatus() {
        return status_;
    }

    public Customer getOrderer() {
        return orderer_;
    }

    public Cart getCart() {
    	return cart_;
    }
}
