/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.controllers;

import edu.data.Author;
import edu.data.Credentials;
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
    
    private Author author = new Author();
    
    public Author getAuthor() {
        return author;
    }
    
    public List<Author> getAuthorList() {
        return ae.findAll();
    }
    
    public void createAuthor(final String name, final String second, final String last) {
        author = new Author(new Credentials(name, second, last));
        author = ae.create(author);
    }
    
    public List<Author> fuzzyFind(final String search) {
        if (search.isEmpty()) {
            return ae.findByCredentials("", "");
        }
        final String[] split = search.split(" ");
        if (split.length < 2) {
            return ae.findByCredentials(split[0], "");
        } else {
            return ae.findByCredentials(split[0], split[1]);
        }
    }
    
    public void findById() {
        author = ae.findById(author.getId());
    }
    
    public void deleteAuthor(final Author author) {
        ae.delete(author);
    }
    
    public String updateAuthor() {
        ae.update(author);
        return "/admin_pages/modify_author?faces-redirect=true";
    }
}
