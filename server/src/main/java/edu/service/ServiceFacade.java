package edu.service;

import business.*;
import static edu.httpservice.GBooksHttpConnector.executeRequest;
import edu.httpservice.JsonBookObject;
import edu.httpservice.Request;
import exception.BookParseException;
import exception.EntryNotFoundException;
import exception.EntryRedefinitionException;
import exception.IncorrectPasswordException;
import edu.service.*;
import exception.ServiceException;
import java.rmi.RemoteException;

import java.util.GregorianCalendar;
import java.util.List;
import org.json.JSONException;
import rmi.BookStoreService;

/**
 * Created with IntelliJ IDEA.
 * User: alexander
 * Date: 10/22/13
 * Time: 5:05 PM
 * To change this template use File | Settings | File Templates.
 */
public final class ServiceFacade implements BookStoreService {
    @Override
    public User findUser(String login, String password)
            throws EntryNotFoundException, IncorrectPasswordException {
        return UserCatalogue.getUser(login, password);
    }

    @Override
    public void createUser(String login, String password, Credentials credentials)
            throws EntryRedefinitionException {
        UserCatalogue.createUser(login, password, credentials);
    }

    @Override
    public List<String> getPublisherNames() {
        return PublisherCatalogue.getPublisherNames();
    }

    @Override
    public Publisher getPublisher(String name) throws EntryNotFoundException {
        return PublisherCatalogue.getPublisher(name);
    }

    @Override
    public void deletePublisher(Publisher pub) {
        PublisherCatalogue.deletePublisher(pub);
    }

    @Override
    public void createPublisher(String name) {
        PublisherCatalogue.createPublisher(name);
    }

    @Override
    public void updatePublisher(Publisher pub) {
        PublisherCatalogue.updatePublisher(pub);
    }

    @Override
    public void createBook(Book book, int amount) throws EntryRedefinitionException {
        BookCatalogue.createBook(book, amount);
    }

    @Override
    public Book parseBook(String name, String author, String genre, 
                          String pubName, String isbn, String price,
                          GregorianCalendar date, String amount)
            throws BookParseException {
        return BookInfoParser.parseBook(name, author, genre, pubName, isbn, price, date, amount);
    }

    @Override
    public List<Book> getBooks(String search) {
        return BookCatalogue.getBooks(search);
    }

    @Override
    public Book getBook(String isbn) {
        return BookCatalogue.getBook(isbn);
    }

    @Override
    public int getAmountOfBook(Book book) throws EntryNotFoundException {
        return BookCatalogue.getAmount(book);
    }

    @Override
    public void setAmountOfBook(Book book, int amount) {
        BookCatalogue.setAmount(book, amount);
    }

    @Override
    public void deleteBook(Book book) {
        BookCatalogue.deleteBook(book);
    }

    @Override
    public void updateBook(Book book) {
        BookCatalogue.updateBook(book);
    }

    @Override
    public int getNumOfSoldBooks(Book book) throws EntryNotFoundException {
        return BookCatalogue.getNumSold(book);
    }

    @Override
    public void createOrder(Customer user) {
        OrderCatalogue.createOrder(user);
    }

    @Override
    public GoogleBook getGoogleBook(Isbn isbn) throws ServiceException {
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
