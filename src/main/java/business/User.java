/**
 * 
 */
package business;

import org.eclipse.jdt.annotation.NonNull;

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
	
	public User(@NonNull final String login, @NonNull final String password, 
			@NonNull final String name, final String secondName, 
			@NonNull final String familyName, final String email) {
		login_ = login;
		passwordHash_ = password.hashCode();
		name_ = name;
		secondName_ = secondName;
		familyName_ = familyName;
		email_ = email;
	}
}
