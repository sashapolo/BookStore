/**
 * 
 */
package business;

import java.util.LinkedList;
import java.util.List;

/**
 * @author alexander
 * 
 */
public class Customer extends User {
	protected Discount personalDiscount_;
	protected List<Order> orders_;

	public Customer(String login, String password, String name,
			String secondName, String familyName, String email, int discount) {
		super(login, password, name, secondName, familyName, email);
		personalDiscount_ = new Discount(discount);
		orders_ = new LinkedList<>();
	}

	public Discount getPersonalDiscount() {
		return personalDiscount_;
	}
}
