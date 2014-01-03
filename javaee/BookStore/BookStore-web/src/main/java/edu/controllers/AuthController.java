/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.controllers;

import edu.data.User;
import edu.ejb.UserEjb;
import edu.util.MessageManager;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;

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

    public User getUser() {
        return user;
    }
    
    public void setUser(final User user) {
        this.user = user;
    }
    
    public String authUser(final String login, final String password) {
        if (user == null || !user.getLogin().equals(login)) {
            user = ue.findByLogin(login);
        }
        if (user.getPassword() != password.hashCode()) {
            MessageManager.createContextError("login_form:password", "Invalid password");
            return "";
        }
        return user.isAdmin() ? "admin_home?faces-redirect=true" : "home"; 
    }
    
    public String logout() {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        user = null;
        return "signin";
    }
}
