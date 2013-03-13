/**
 * 
 */
package business;

import dbwrappers.RequestQueue;

/**
 * @author alexander
 * 
 */
public class Administrator extends User {
    public Administrator(long id, String login, String password, String name, String secondName,
            String email) {
        super(id, login, password, name, secondName, email);
    }
    
    public Request getLatestRequest() {
        return RequestQueue.INSTANCE.pop();
    }
}
