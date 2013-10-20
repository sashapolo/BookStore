/**
 * 
 */
package business;

import util.EnumConverter;

import java.util.GregorianCalendar;


/**
 * @author alexander
 * 
 */
public final class Order {
    private final Builder builder;
    
    private Order(final Builder builder) {
        assert(builder != null);
        this.builder = builder;
    }

    public int getId() {
        return builder.id;
    }

    public GregorianCalendar getDateCreated() {
        return (GregorianCalendar) builder.dateCreated.clone();
    }

    public double getPrice() {
        return builder.cartPrice;
    }

    public OrderStatus getStatus() {
        return builder.status;
    }

    public Customer getOrderer() {
        return builder.customer;
    }

    public Cart getCart() {
    	return builder.cart;
    }

    public void setId(final int id) {
        builder.id = id;
    }

    public final static class Builder {
        private int id = 0;
        private GregorianCalendar dateCreated = new GregorianCalendar();
        private final Cart cart;
        private final double cartPrice;
        private OrderStatus status = OrderStatus.CREATED;
        private final Customer customer;

        public Builder(final Cart cart, final Customer customer) {
            assert (cart != null);
            assert (customer != null);
            assert (!cart.isEmpty()): "Creating an order with an empty cart";

            this.cart = cart;
            cartPrice = cart.getPrice(customer.getPersonalDiscount());
            this.customer = customer;
        }

        public Builder id(final int id) {
            this.id = id;
            return this;
        }
        
        public Builder dateCreated(final GregorianCalendar date) {
            dateCreated = (GregorianCalendar) date.clone();
            return this;
        }

        public Builder status(final OrderStatus status) {
            this.status = status;
            return this;
        }

        public Order build() {
            return new Order(this);
        }
    }

    public enum OrderStatus implements EnumConverter {
        //TODO add some statuses
        CREATED(0);

        private final int id;
        OrderStatus(final int id) { this.id = id; }
        
        @Override
        public int convert() { return id; }
    }

}
