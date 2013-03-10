/**
 * 
 */
package business;

/**
 * @author alexander
 *
 */
public class Customer extends User {
	public Customer(String login, String password, String name, 
			String secondName, String familyName, String email, int discount) {
		super(login, password, name, secondName, familyName, email, discount);
	}
}
