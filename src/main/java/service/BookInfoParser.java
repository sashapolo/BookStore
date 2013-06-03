/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import business.Book;
import business.Isbn;
import business.Isbn13;
import business.Publisher;
import business.WrongFormatException;
import java.util.GregorianCalendar;

/**
 *
 * @author alexander
 */
public class BookInfoParser {
    public static Book parseBook(final String name,
                                 final String author,
                                 final String genre,
                                 final String pubName,
                                 final String isbn,
                                 final String price,
                                 final GregorianCalendar date,
                                 final String amount) throws BookParseException {
        if (name.isEmpty() || author.isEmpty() || genre.isEmpty() || pubName.isEmpty()) {
            throw new BookParseException("Some required fields are empty");
        }
        Isbn parsedIsbn;
        try {
            parsedIsbn = new Isbn13(isbn);
        } catch (WrongFormatException ex) {
            throw new BookParseException(ex.getMessage());
        }
        double parsedPrice;
        try {
            parsedPrice = Double.valueOf(price);
        } catch (NumberFormatException e) {
            throw new BookParseException("Price must be a floating point value");
        }
        int parsedAmount;
        try {
            parsedAmount = Integer.valueOf(amount);
            if (parsedAmount <= 0) throw new NumberFormatException("Amount must be greater than 0");
        } catch (NumberFormatException e) {
            throw new BookParseException(e.getMessage());
        }
        Publisher pub = null;
        try {
            pub = PublisherCatalogue.getPublisher(pubName);
        } catch (EntryNotFoundException ex) {
            throw new BookParseException(ex.getMessage());
        }
        
        return new Book.Builder(name, author, genre, pub, date, parsedIsbn, parsedPrice).build();
    }

    private BookInfoParser() {}
    
}
