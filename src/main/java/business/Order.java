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
	private int id_;
	private final GregorianCalendar dateCreated_;
	private final Basket busket_;
	private final BigDecimal busketPrice_;
	private OrderStatus status_;
	private final Customer orderer_;

	public Order(int id, GregorianCalendar dateCreated, Basket basket, Customer orderer) {
		assert (id >= 0);
		assert (dateCreated != null);
		assert (basket != null);
		assert (orderer != null);

		id_ = id;
		dateCreated_ = dateCreated;
		busket_ = basket;
		busketPrice_ = basket.getPrice(orderer.getPersonalDiscount());
		orderer_ = orderer;
		status_ = OrderStatus.CREATED;
	}

	public int getId() {
		return id_;
	}

	public GregorianCalendar getDateCreated() {
		return dateCreated_;
	}

	public Basket getBusket() {
		return busket_;
	}

	public BigDecimal getPrice() {
		return busketPrice_;
	}

	public BigDecimal getDisplayedPrice() {
		return busketPrice_.setScale(2, BigDecimal.ROUND_HALF_UP);
	}

	public OrderStatus getStatus() {
		return status_;
	}

	public Customer getOrderer() {
		return orderer_;
	}

}
