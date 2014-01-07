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
import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author alexander
 */
@Named
@ViewScoped
public class UserSearchBookController implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @EJB
    private transient BookEjb be;
    @EJB
    private transient StockEjb se;
    
    @Inject
    private transient Logger logger;
    
    private String searchStr;
    private transient List<Book> bookList;
    private Book book;
    private Stock stock = new Stock();
    
    public Book getBook() {
        return book;
    }
    
    public String getSearchStr() {
        return searchStr;
    }

    public void setSearchStr(String searchStr) {
        if (!searchStr.equals(this.searchStr)) {
            this.searchStr = searchStr;
            bookList = null;
        }
    }
    
    public int getAmount() {
        return stock.getAmount();
    }
    
    public void view(final Book book) {
        this.book = book;
        stock = se.findByBook(book);
    }
    
    public List<Book> getBookList() {
        if (bookList == null) {
            logger.log(Level.INFO, "searching for {0}", searchStr);
            bookList = be.fuzzyFind(searchStr == null ? "" : searchStr);
        }
        return Collections.unmodifiableList(bookList);
    }
}
