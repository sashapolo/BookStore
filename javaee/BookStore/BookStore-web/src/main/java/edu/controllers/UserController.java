/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.controllers;

import edu.data.User;
import edu.ejb.UserEjb;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.inject.Model;

/**
 *
 * @author alexander
 */
@Model
public class UserController {
    @EJB
    private UserEjb ue;
    
    private User user = new User.Builder().build();
    
    public User getUser() {
        return user;
    }
    
    public List<User> findAllUsers() {
        return ue.findAll();
    }
    
    public List<User> findByLogin(final String login) {
        return ue.findByLogin(login);
    }
    
    public void findById() {
        user = ue.findById(user.getId());
    }
    
    public void deleteUser(final User user) {
        ue.delete(user);
    }
    
    public String updateUser() {
        user = ue.update(user);
        return "modify_user.xhtml?faces-redirect=true";
    }
}
