/**
 * 
 */
package business;

import java.util.HashMap;
import java.util.Map;

/**
 * @author alexander
 *
 */
public class Publisher {
	private final int id_;
	private String name_;
	private String email_;
	private Map<Integer, Request> requests_;
	
	public Publisher(int id, String name, String email) {
		assert(name != null);
		assert(id >= 0);
		
		id_ = id;
		name_ = name;
		email_ = email;
		requests_ = new HashMap<>();
	}

	public int getId() {
		return id_;
	}

	public String getName() {
		return name_;
	}

	public String getEmail() {
		return email_;
	}
	
	public void addRequest(Request request) {
		requests_.put(request.getId(), request);
	}
}
