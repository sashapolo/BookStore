/**
 * 
 */
package business;

import org.eclipse.jdt.annotation.NonNull;

/**
 * @author alexander
 *
 */
public class Publisher {
	private int id_;
	private String name_;
	private String email_;
	
	public Publisher(int id, @NonNull final String name, final String email) {
		id_ = id;
		name_ = name;
		email_ = email;
	}
}
