/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.controllers;

import edu.data.Book;
import edu.data.BookOrderEntry;
import java.io.Serializable;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author alexander
 */
@Named
@SessionScoped
public class CartController implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Inject
    private transient Logger logger;
    
    private List<BookOrderEntry> cart = new LinkedList<>();
    
    public void addEntry(final Book book) {
        logger.log(Level.INFO, "adding {0} to cart", book);
        cart.add(new BookOrderEntry(book, 1));
    }
    
    public List<BookOrderEntry> getCart() {
        return Collections.unmodifiableList(cart);
    }
}
