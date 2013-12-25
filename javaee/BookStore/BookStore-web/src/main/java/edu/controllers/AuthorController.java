/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.controllers;

import edu.data.Author;
import edu.ejb.AuthorEjb;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.inject.Model;

/**
 *
 * @author alexander
 */
@Model
public class AuthorController {
    @EJB
    private AuthorEjb ae;
    
    public List<Author> getAuthorList() {
        return ae.findAll();
    }
}
