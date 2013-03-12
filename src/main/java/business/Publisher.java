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
    private final long id_;
    private String name_;
    private String email_;
    private final Map<Long, Request> requests_;

    public Publisher(long id, String name, String email) {
        assert (name != null);

        id_ = id;
        name_ = name;
        email_ = email;
        requests_ = new HashMap<>();
    }

    public long getId() {
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
