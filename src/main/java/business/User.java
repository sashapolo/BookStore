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

	public User(String login, String password, String name, String secondName,
			String familyName, String email) {
		assert (login != null);
		assert (password != null);
		assert (name != null);
		assert (familyName != null);

		login_ = login;
		passwordHash_ = password.hashCode();
		name_ = name;
		secondName_ = secondName;
		familyName_ = familyName;
		email_ = email;
	}
}
