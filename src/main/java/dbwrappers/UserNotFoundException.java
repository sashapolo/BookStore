package dbwrappers;

/**
 * Created with IntelliJ IDEA.
 * User: alexander
 * Date: 5/26/13
 * Time: 3:49 PM
 * To change this template use File | Settings | File Templates.
 */
public class UserNotFoundException extends Exception {
    public UserNotFoundException(final String message) { super(message); }
}
