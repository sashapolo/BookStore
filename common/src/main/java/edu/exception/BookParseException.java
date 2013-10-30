/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.exception;

/**
 *
 * @author alexander
 */
public class BookParseException extends Exception {
    private static final long serialVersionUID = 1726469632752798961L;

    public BookParseException(final String msg) {
        super(msg);
    }
}
