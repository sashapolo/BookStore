/**
 * 
 */
package business;

import java.util.LinkedList;

/**
 * @author alexander
 *
 */
public enum RequestQueue {
    INSTANCE;
    
    private final LinkedList<Request> queue_ = new LinkedList<>();

    public Request pop() {
        return queue_.pop();
    }

    public void push(Request e) {
        queue_.push(e);
    }
}
