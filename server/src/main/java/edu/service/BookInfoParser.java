/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.service;

import business.Book;
import business.Isbn;
import business.Publisher;
import exception.BookParseException;
import exception.EntryNotFoundException;

import java.util.GregorianCalendar;

/**
 *
 * @author alexander
 */
final class BookInfoParser {
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
        if (!Isbn.isValid(isbn)) {
            throw new BookParseException("Invalid isbn format");
        }

        final Isbn parsedIsbn = new Isbn(isbn);

        final double parsedPrice;
        try {
            parsedPrice = Double.valueOf(price);
        } catch (NumberFormatException e) {
            throw new BookParseException(e.getMessage());
        }

        final int parsedAmount;
        try {
            parsedAmount = Integer.valueOf(amount);
            if (parsedAmount <= 0) {
                throw new BookParseException("Amount must be greater than 0");
            }
        } catch (NumberFormatException e) {
            throw new BookParseException(e.getMessage());
        }

        final Publisher pub;
        try {
            pub = PublisherCatalogue.getPublisher(pubName);
        } catch (EntryNotFoundException ex) {
            throw new BookParseException(ex.getMessage());
        }
        
        return new Book.Builder(name, author, genre, pub, date, parsedIsbn, parsedPrice).build();
    }

    private BookInfoParser() {}
}
