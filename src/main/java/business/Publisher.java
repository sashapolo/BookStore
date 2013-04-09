/**
 * 
 */
package business;

import business.requests.Request;
import dbwrappers.RequestQueue;

/**
 * @author alexander
 * 
 */
public class Publisher {
    private final long id_ = IdDispatcher.INSTANCE.getNewPublisherId();
    private final String name_;
    private final String email_;

    public Publisher(final String name, final String email) {
        assert (name != null);

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

    public void postRequest(final Request request) {
        RequestQueue.INSTANCE.push(request);
    }
}
