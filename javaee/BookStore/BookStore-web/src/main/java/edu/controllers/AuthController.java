/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.controllers;

import edu.data.Credentials;
import edu.data.User;
import edu.ejb.UserEjb;
import java.io.Serializable;
import java.util.List;
import java.util.Set;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

/**
 *
 * @author alexander
 */
@Named
@SessionScoped
public class AuthController implements Serializable {
    private static final long serialVersionUID = 1L;

    @EJB
    private transient UserEjb ue;

    private User user;
    private boolean loginExists = false;
    private boolean passwordCorrect = true;
    
    public boolean isPasswordCorrect() {
        return passwordCorrect;
    }

    public User getUser() {
        return user;
    }
    
    public boolean isLoginExists() {
        return loginExists;
    }
    
    public String createUser(final String login, final String password, final String name,
            final String secondName, final String lastName, final String email) {
        
        if (!ue.findByLogin(login).isEmpty()) {
            createContextError("Already existing login");
            loginExists = true;
            return "";
        }

        try {
            final Credentials cred = new Credentials(name, secondName, lastName);
            user = new User.Builder(login, password.hashCode(), cred, email).build();
            user = ue.create(user);
        } catch (EJBException e) {
            processEjbException(e);
            return "";
        }
        return "signin";
    }
    
    public String authUser(final String login, final String password) {
        user = ue.findByLogin(login).get(0);
        passwordCorrect = user.getPassword() == password.hashCode();
        if (!passwordCorrect) {
            createContextError("Invalid password");
            return "";
        }
        return user.isAdmin() ? "admin_home" : "home"; 
    }
    
    public String logout() {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "signin";
    }
    
    private void processEjbException(final EJBException e) {
        final ConstraintViolationException ce = (ConstraintViolationException) e.getCause();
        final Set<ConstraintViolation<?>> violations = ce.getConstraintViolations();
        for (final ConstraintViolation<?> violation : violations) {
            createContextError(violation.getMessage());
        }
    }
    
    private void createContextError(final String message) {
        final FacesMessage msg = new FacesMessage(message);
        msg.setSeverity(FacesMessage.SEVERITY_ERROR);
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
}
