/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.httpservice;

/**
 *
 * @author alexander
 */
@SuppressWarnings("serial")
public class ServiceException extends Exception {
    public ServiceException(String msg) {
        super(msg);
    }
}
