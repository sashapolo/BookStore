/**
 * 
 */
package business;

import java.util.GregorianCalendar;
import util.EnumConverter;


/**
 * @author alexander
 * 
 */
public class Order {
    private final Builder builder_;
    
    private Order(final Builder builder) {
        assert(builder != null);
        builder_ = builder;
    }

    public int getId() {
        return builder_.id_;
    }

    public GregorianCalendar getDateCreated() {
        return builder_.dateCreated_;
    }

    public double getPrice() {
        return builder_.cartPrice_;
    }

    public OrderStatus getStatus() {
        return builder_.status_;
    }

    public Customer getOrderer() {
        return builder_.orderer_;
    }

    public Cart getCart() {
    	return builder_.cart_;
    }

    public void setId(final int id) {
        builder_.id_ = id;
    }

    public static class Builder {
        private int id_= 0;
        private GregorianCalendar dateCreated_ = new GregorianCalendar();
        private final Cart cart_;
        private final double cartPrice_;
        private OrderStatus status_ = OrderStatus.CREATED;
        private final Customer orderer_;

        public Builder(final Cart cart, final Customer orderer) {
            assert (cart != null);
            assert (orderer != null);
            assert (!cart.isEmpty()): "Creating an order with an empty cart";

            cart_ = cart;
            cartPrice_ = cart.getPrice(orderer.getPersonalDiscount());
            orderer_ = orderer;
        }

        public Builder id(final int id) {
            id_ = id;
            return this;
        }
        
        public Builder dateCreated(final GregorianCalendar date) {
            dateCreated_ = date;
            return this;
        }

        public Builder status(final OrderStatus status) {
            status_ = status;
            return this;
        }

        public Order build() {
            return new Order(this);
        }
    }

    public enum OrderStatus implements EnumConverter {
        // TODO add some statuses
        CREATED(0);

        private final int id_;
        OrderStatus(final int id) { id_ = id; }
        
        @Override
        public int convert() { return id_; }
    }

}
