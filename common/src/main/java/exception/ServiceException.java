/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package exception;

/**
 *
 * @author alexander
 */
public class ServiceException extends Exception {
    private static final long serialVersionUID = 4574000203155339457L;

    public ServiceException(final String msg) {
        super(msg);
    }
}
