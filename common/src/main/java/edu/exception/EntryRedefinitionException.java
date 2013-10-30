/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.exception;

/**
 *
 * @author alexander
 */
public class EntryRedefinitionException extends Exception {
    private static final long serialVersionUID = -5029971903863242303L;

    public EntryRedefinitionException(final String message) { super(message); }
}
