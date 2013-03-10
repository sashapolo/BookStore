/**
 * 
 */
package business;

/**
 * @author alexander
 *
 */
public class Administrator extends User {
	public final static int ADMIN_DISCOUNT = 60;
	
	public Administrator(String login, String password, String name, 
			String secondName, String familyName, String email) {
		super(login, password, name, secondName, familyName, email, ADMIN_DISCOUNT);
	}
}
