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
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

/**
 *
 * @author alexander
 */
@Named
@SessionScoped
public class CartController implements Serializable {
    private static final long serialVersionUID = 1L;
    private List<BookOrderEntry> cart;
    
    public void addEntry(final Book book) {
        cart.add(new BookOrderEntry(book, 1));
    }
    
    public List<BookOrderEntry> getCart() {
        return Collections.unmodifiableList(cart);
    }
}
