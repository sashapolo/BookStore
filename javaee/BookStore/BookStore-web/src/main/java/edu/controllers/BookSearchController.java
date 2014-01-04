/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.controllers;

import edu.data.Book;
import edu.ejb.BookEjb;
import java.util.Collections;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.inject.Model;

/**
 *
 * @author alexander
 */
@Model
public class BookSearchController {
    @EJB
    private BookEjb be;
    
    private String searchStr;
    private List<Book> bookList = Collections.emptyList();

    public String getSearchStr() {
        return searchStr;
    }

    public void setSearchStr(String searchStr) {
        this.searchStr = searchStr;
    }

    public List<Book> getBookList() {
        return Collections.unmodifiableList(bookList);
    }
    
    public void search() {
        bookList = be.fuzzyFind(searchStr == null ? "" : searchStr);
    }
}
