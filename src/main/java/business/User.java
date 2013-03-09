/**
 * 
 */
package business;

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
	
	public User(final String login, final String password, final String name,
			final String secondName, final String familyName, final String email) {
		login_ = login;
		passwordHash_ = password.hashCode();
		name_ = name;
		secondName_ = secondName;
		familyName_ = familyName;
		email_ = email;
	}
}
