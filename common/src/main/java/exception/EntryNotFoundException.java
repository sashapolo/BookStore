package exception;

/**
 * Created with IntelliJ IDEA.
 * User: alexander
 * Date: 5/26/13
 * Time: 3:49 PM
 * To change this template use File | Settings | File Templates.
 */
public class EntryNotFoundException extends Exception {
    private static final long serialVersionUID = -2166287089772809405L;

    public EntryNotFoundException(final String message) { super(message); }
}
