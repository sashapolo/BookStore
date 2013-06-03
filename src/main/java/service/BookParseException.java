/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

/**
 *
 * @author alexander
 */
@SuppressWarnings("serial")
public class BookParseException extends Exception {

    /**
     * Creates a new instance of
     * <code>BookParseException</code> without detail message.
     */
    public BookParseException() {
    }

    /**
     * Constructs an instance of
     * <code>BookParseException</code> with the specified detail message.
     *
     * @param msg the detail message.
     */
    public BookParseException(String msg) {
        super(msg);
    }
}
