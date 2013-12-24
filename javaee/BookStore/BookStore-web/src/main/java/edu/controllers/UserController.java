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
public class UserController implements Serializable {

    private static final long serialVersionUID = 1L;

    @EJB
    private transient UserEjb be;

    private User user;
    private boolean registered = false;

    public boolean isRegistered() {
        return registered;
    }

    public void setRegistered(boolean registered) {
        this.registered = registered;
    }

    public User getUser() {
        return user;
    }
    
    public String createUser(final String login, final String password, final String name,
            final String secondName, final String lastName, final String email) {
        if (!be.findByLogin(login).isEmpty()) {
            return "error.xhtml";
        }

        try {
            final Credentials cred = new Credentials(name, secondName, lastName);
            user = new User.Builder(login, password.hashCode(), cred, email).build();
            user = be.create(user);
            registered = true;
        } catch (EJBException e) {
            final FacesContext fc = FacesContext.getCurrentInstance();
            final ConstraintViolationException ce = (ConstraintViolationException) e.getCause();
            final Set<ConstraintViolation<?>> violations = ce.getConstraintViolations();
            for (final ConstraintViolation<?> violation : violations) {
                fc.addMessage("register_form:email", new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                        "error!", violation.getMessage()));
            }
            return "";
        }
        return "signin.xhtml";
    }
}
