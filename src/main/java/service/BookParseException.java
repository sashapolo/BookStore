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
    public BookParseException(final String msg) {
        super(msg);
    }
}
