package edu.service;

import business.*;
import edu.httpservice.JsonBookObject;
import edu.httpservice.Request;
import exception.*;
import org.json.JSONException;
import rmi.BookStoreService;

import java.util.GregorianCalendar;
import java.util.List;

import static edu.httpservice.GBooksHttpConnector.executeRequest;

/**
 * Created with IntelliJ IDEA.
 * User: alexander
 * Date: 10/22/13
 * Time: 5:05 PM
 * To change this template use File | Settings | File Templates.
 */
public final class ServiceFacade implements BookStoreService {
    @Override
    public User findUser(final String login, final String password)
            throws EntryNotFoundException, IncorrectPasswordException {
        return UserCatalogue.getUser(login, password);
    }

    @Override
    public void createUser(final String login, final String password, final Credentials credentials)
            throws EntryRedefinitionException {
        UserCatalogue.createUser(login, password, credentials);
    }

    @Override
    public List<String> getPublisherNames() {
        return PublisherCatalogue.getPublisherNames();
    }

    @Override
    public Publisher getPublisher(final String name) throws EntryNotFoundException {
        return PublisherCatalogue.getPublisher(name);
    }

    @Override
    public void deletePublisher(final Publisher pub) {
        PublisherCatalogue.deletePublisher(pub);
    }

    @Override
    public void createPublisher(final String name) {
        PublisherCatalogue.createPublisher(name);
    }

    @Override
    public void updatePublisher(final Publisher pub) {
        PublisherCatalogue.updatePublisher(pub);
    }

    @Override
    public void createBook(final Book book, final int amount) throws EntryRedefinitionException {
        BookCatalogue.createBook(book, amount);
    }

    @Override
    public Book parseBook(final String name, final String author, final String genre,
                          final String pubName, final String isbn, final String price,
                          final GregorianCalendar date, final String amount)
            throws BookParseException {
        return BookInfoParser.parseBook(name, author, genre, pubName, isbn, price, date, amount);
    }

    @Override
    public List<Book> getBooks(final String search) {
        return BookCatalogue.getBooks(search);
    }

    @Override
    public Book getBook(final String isbn) {
        return BookCatalogue.getBook(isbn);
    }

    @Override
    public int getAmountOfBook(final Book book) throws EntryNotFoundException {
        return BookCatalogue.getAmount(book);
    }

    @Override
    public void setAmountOfBook(final Book book, final int amount) {
        BookCatalogue.setAmount(book, amount);
    }

    @Override
    public void deleteBook(final Book book) {
        BookCatalogue.deleteBook(book);
    }

    @Override
    public void updateBook(final Book book) {
        BookCatalogue.updateBook(book);
    }

    @Override
    public int getNumOfSoldBooks(final Book book) throws EntryNotFoundException {
        return BookCatalogue.getNumSold(book);
    }

    @Override
    public void createOrder(final Customer user) {
        OrderCatalogue.createOrder(user);
    }

    @Override
    public GoogleBook getGoogleBook(final Isbn isbn) throws ServiceException {
        try {
            final Request request = new Request(isbn);
            final JsonBookObject b = new JsonBookObject(executeRequest(request));
            
            final String name = b.getName();
            final String author = b.getAuthor();
            final String date = b.getPublishedDate();
            final String publisher = b.getPublisher();
            final double rating = b.getRating();
            
            return new GoogleBook(name, author, publisher, date, rating);
        } catch (JSONException e) {
            throw new ServiceException(e.getMessage());
        }
    }
}
