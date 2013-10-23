package service;

/**
 * Created with IntelliJ IDEA.
 * User: alexander
 * Date: 5/26/13
 * Time: 3:53 PM
 * To change this template use File | Settings | File Templates.
 */
public class IncorrectPasswordException extends Exception {
    private static final long serialVersionUID = -897263154997206528L;

    public IncorrectPasswordException() { super("Incorrect password"); }
}
