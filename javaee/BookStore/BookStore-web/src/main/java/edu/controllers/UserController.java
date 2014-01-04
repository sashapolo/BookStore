/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.controllers;

import edu.data.User;
import edu.ejb.UserEjb;
import edu.util.MessageManager;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.inject.Model;
import javax.inject.Inject;

/**
 *
 * @author alexander
 */
@Model
public class UserController {
    @EJB
    private UserEjb ue;
    
    @Inject
    private AuthController auth;
    
    private User user = new User.Builder().build();
    
    public User getUser() {
        return user;
    }
    
    public List<User> findAllUsers() {
        return ue.findAll();
    }
    
    public List<User> find(final String search) {
        return ue.fuzzyFind(search);
    }
    
    public void findById() {
        user = ue.findById(user.getId());
    }
    
    public void createAdmin() {
        user.setAdmin(true);
        user = ue.create(user);
    }
    
    public String registerUser() {
        if (ue.findByLogin(user.getLogin()) != null) {
            MessageManager.createContextError("register_form:login", "Already existing login");
            return "";
        }
        user = ue.create(user);
        auth.setUser(user);
        return "/signin?faces-redirect=true";
    }
    
    public void deleteUser(final User user) {
        ue.delete(user);
    }
    
    public String updateUser() {
        user = ue.update(user);
        return "/admin_pages/modify_user?faces-redirect=true";
    }
}
