/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.controllers;

import edu.data.Book;
import edu.data.Stock;
import edu.ejb.BookEjb;
import edu.ejb.StockEjb;
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
    @EJB
    private StockEjb se;
    
    @Inject
    private Logger logger;
    
    private Book book = new Book.Builder().build();
    private Stock stock = new Stock();
        
    public Book getBook() {
        return book;
    }
    
    public void setBook(final Book book) {
        this.book = book;
        stock = se.findByBook(book);
    }
    
    public int getAmount() {
        return stock.getAmount();
    }
    
    public void setAmount(final int amount) {
        stock.setAmount(amount);
    }
    
    public void createBook() {
        logger.log(Level.INFO, "creating book: {0}", book);
        book = be.create(book);
        stock.setBook(book);
        stock = se.create(stock);
    }
    
    public List<Book> findBooks(final String search) {
        return be.fuzzyFind(search);
    }
    
    public void deleteBook(final Book book) {
        se.delete(se.findByBook(book));
        be.delete(book);
    }
    
    public String updateBook() {
        book = be.update(book);
        stock = se.update(stock);
        return "/admin_pages/modify_book?faces-redirect=true";
    }
    
    public void findById() {
        book = be.findById(book.getId());
        stock = se.findByBook(book);
        logger.log(Level.INFO, "Found book with title: {0}", book.getTitle());
    }
}
