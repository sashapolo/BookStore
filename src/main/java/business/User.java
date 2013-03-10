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
public class User {
	protected String login_;
	protected int passwordHash_;
	protected String name_;
	protected String secondName_;
	protected String familyName_;
	protected String email_;
	protected Discount personalDiscount_;
	protected List<Order> orders_; 
	
	public User(String login, String password, String name, String secondName, 
			String familyName, String email, int discount) {
		assert(login != null);
		assert(password != null);
		assert(name != null);
		assert(familyName != null);
		
		login_ = login;
		passwordHash_ = password.hashCode();
		name_ = name;
		secondName_ = secondName;
		familyName_ = familyName;
		email_ = email;
		personalDiscount_ = new Discount(discount);
		orders_ = new LinkedList<>();
	}
	
	public Discount getPersonalDiscount() {
		return personalDiscount_;
	}
}
