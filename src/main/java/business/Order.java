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
    private final long id_;
    private final GregorianCalendar dateCreated_;
    private final Cart basket_;
    private final BigDecimal basketPrice_;
    private OrderStatus status_;
    private final Customer orderer_;

    public Order(long id, GregorianCalendar dateCreated, Cart cart, Customer orderer) {
        assert (dateCreated != null);
        assert (cart != null);
        assert (orderer != null);

        id_ = id;
        dateCreated_ = dateCreated;
        basket_ = cart;
        basketPrice_ = cart.getPrice(orderer.getPersonalDiscount());
        orderer_ = orderer;
        status_ = OrderStatus.CREATED;
    }

    public long getId() {
        return id_;
    }

    public GregorianCalendar getDateCreated() {
        return dateCreated_;
    }

    public Cart getBasket() {
        return basket_;
    }

    public BigDecimal getPrice() {
        return basketPrice_;
    }

    public BigDecimal getDisplayedPrice() {
        return basketPrice_.setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    public OrderStatus getStatus() {
        return status_;
    }

    public Customer getOrderer() {
        return orderer_;
    }

}
