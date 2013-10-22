package service;

import business.*;

import java.util.GregorianCalendar;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: alexander
 * Date: 10/22/13
 * Time: 5:05 PM
 * To change this template use File | Settings | File Templates.
 */
public final class ServiceFacade {
    public static User findUser(final String login, final String password)
            throws EntryNotFoundException, IncorrectPasswordException {
        return UserCatalogue.getUser(login, password);
    }

    public static void createUser(final String login,
                                  final String password,
                                  final Credentials credentials)
            throws EntryRedefinitionException {
        UserCatalogue.createUser(login, password, credentials);
    }

    public static List<String> getPublisherNames() {
        return PublisherCatalogue.getPublisherNames();
    }

    public static Publisher getPublisher(final String name)
            throws EntryNotFoundException {
        return PublisherCatalogue.getPublisher(name);
    }

    public static void deletePublisher(final Publisher pub) {
        PublisherCatalogue.deletePublisher(pub);
    }

    public static void createPublisher(final String name) {
        PublisherCatalogue.createPublisher(name);
    }

    public static void updatePublisher(final Publisher pub) {
        PublisherCatalogue.updatePublisher(pub);
    }

    public static void createBook(final Book book, int amount)
            throws EntryRedefinitionException {
        BookCatalogue.createBook(book, amount);
    }

    public static Book parseBook(final String name,
                                 final String author,
                                 final String genre,
                                 final String pubName,
                                 final String isbn,
                                 final String price,
                                 final GregorianCalendar date,
                                 final String amount) throws BookParseException {
        return BookInfoParser.parseBook(name, author, genre, pubName, isbn, price, date, amount);
    }

    public static List<Book> getBooks(final String search) {
        return BookCatalogue.getBooks(search);
    }

    public static Book getBook(final String isbn) {
        return BookCatalogue.getBook(isbn);
    }

    public static int getAmountOfBook(final Book book) throws EntryNotFoundException {
        return BookCatalogue.getAmount(book);
    }

    public static void setAmountOfBook(final Book book, int amount) {
        BookCatalogue.setAmount(book, amount);
    }

    public static void deleteBook(final Book book) {
        BookCatalogue.deleteBook(book);
    }

    public static void updateBook(final Book book) {
        BookCatalogue.updateBook(book);
    }

    public static int getNumOfSoldBooks(final Book book) throws EntryNotFoundException {
        return BookCatalogue.getNumSold(book);
    }

    public static void createOrder(final Customer user) {
        OrderCatalogue.createOrder(user);
    }

    private ServiceFacade() {}
}
