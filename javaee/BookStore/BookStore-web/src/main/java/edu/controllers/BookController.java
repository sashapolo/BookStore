/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.controllers;

import edu.data.Author;
import edu.data.Book;
import edu.data.Isbn;
import edu.data.Publisher;
import edu.ejb.BookEjb;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.enterprise.inject.Model;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
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
    
    private Publisher pub;
    private Author author;
    
    public Publisher getPub() {
        return pub;
    }

    public void setPub(Publisher pub) {
        this.pub = pub;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }
    
    public void createBook(final String title, final String isbn, final String genre, 
            final String date, final String price, final String descr) {
        double p = Double.valueOf(price);
        final Isbn i = new Isbn(isbn);
        
        final SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        final Calendar c = Calendar.getInstance();
        try {
            c.setTime(sdf.parse(date));
        } catch (ParseException e) {
            final FacesMessage msg = new FacesMessage("Invalid date format");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext.getCurrentInstance().addMessage(null, msg);
            return;
        }
        
        final Book book = new Book.Builder(i, title, author).description(descr).genre(genre)
                .price(p).publicationDate(c).publisher(pub).build();
        be.create(book);
    }
    
    
}
