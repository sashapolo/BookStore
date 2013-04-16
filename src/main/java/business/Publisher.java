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
public class Publisher extends User {
    public Publisher(final int id,
                     final String login,
                     final String password,
                     final String name,
                     final String email) {
        super(id, login, password, name, email);
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
