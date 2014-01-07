/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.util;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;

/**
 *
 * @author alexander
 */
public class MessageManager {
    public static void error(final String message) {
        createMsg(null, message, FacesMessage.SEVERITY_ERROR);
    }
    
    public static void error(final String id, final String message) {
        createMsg(id, message, FacesMessage.SEVERITY_ERROR);
    }
    
    public static void info(final String message) {
        createMsg(null, message, FacesMessage.SEVERITY_INFO);
    }
    
    public static void info(final String id, final String message) {
        createMsg(id, message, FacesMessage.SEVERITY_INFO);
    }
    
    private static void createMsg(final String id, final String message, final Severity severity) {
        final FacesMessage msg = new FacesMessage(message);
        msg.setSeverity(severity);
        FacesContext.getCurrentInstance().addMessage(id, msg);
    }

    private MessageManager() {
    }
}
