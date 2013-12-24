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
import javax.enterprise.inject.Model;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

/**
 *
 * @author alexander
 */
@Model
public class AuthController implements Serializable {

    private static final long serialVersionUID = 1L;

    @EJB
    private transient UserEjb be;

    private User user;
    private boolean registered = false;
    private boolean loginExists = false;

    public boolean isRegistered() {
        return registered;
    }

    public void setRegistered(boolean registered) {
        this.registered = registered;
    }

    public User getUser() {
        return user;
    }
    
    public boolean isLoginExists() {
        return loginExists;
    }
    
    public String createUser(final String login, final String password, final String name,
            final String secondName, final String lastName, final String email) {
        if (!be.findByLogin(login).isEmpty()) {
            final FacesContext fc = FacesContext.getCurrentInstance();
            final FacesMessage msg = new FacesMessage("Already existing login");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            fc.addMessage("error", msg);
            loginExists = true;
            return "";
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
                final FacesMessage msg = new FacesMessage(violation.getMessage());
                msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                fc.addMessage("register_form:email", msg);
            }
            return "";
        }
        return "signin.xhtml";
    }
}
