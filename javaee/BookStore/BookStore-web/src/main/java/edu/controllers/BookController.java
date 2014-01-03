/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.controllers;

import edu.data.Book;
import edu.ejb.BookEjb;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.enterprise.inject.Model;
import javax.inject.Inject;

/**
 *
 * @author alexander
 */
@Model
public class BookController {
    @EJB
    private BookEjb be;
    
    @Inject
    private Logger logger;
    
    private Book book = new Book.Builder().build();
        
    public Book getBook() {
        return book;
    }
    
    public void createBook() {
        be.create(book);
    }
    
    public List<Book> findBooks(final String search) {
        return be.fuzzyFind(search);
    }
    
    public void deleteBook(final Book book) {
        be.delete(book);
    }
    
    public String updateBook() {
        book = be.update(book);
        return "modify_book.xhtml?faces-redirect=true";
    }
    
    public void findById() {
        book = be.findById(book.getId());
        logger.log(Level.INFO, "Found book with title: {0}", book.getTitle());
    }
}
