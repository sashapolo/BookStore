/**
 * 
 */
package business;

import dbwrappers.RequestQueue;

/**
 * @author alexander
 * 
 */
public class Publisher {
    private final long id_;
    private String name_;
    private String email_;

    public Publisher(long id, String name, String email) {
        assert (name != null);

        id_ = id;
        name_ = name;
        email_ = email;
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

    public void postRequest(Request request) {
        RequestQueue.INSTANCE.push(request);
    }
}
