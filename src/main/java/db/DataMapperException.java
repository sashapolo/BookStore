package db;

/**
 * Created with IntelliJ IDEA.
 * User: alexander
 * Date: 5/13/13
 * Time: 5:00 PM
 * To change this template use File | Settings | File Templates.
 */
public class DataMapperException extends Exception {
    public DataMapperException() { super(); }
    public DataMapperException(final String message) { super(message); }
    public DataMapperException(final String message, final Throwable cause) { super(message, cause); }
}
