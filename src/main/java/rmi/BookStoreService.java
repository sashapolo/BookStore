package rmi;

import business.*;
import service.BookParseException;
import service.EntryNotFoundException;
import service.EntryRedefinitionException;
import service.IncorrectPasswordException;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: alexander
 * Date: 10/22/13
 * Time: 6:58 PM
 * To change this template use File | Settings | File Templates.
 */
public interface BookStoreService extends Remote {
    User findUser(final String login, final String password)
            throws EntryNotFoundException, IncorrectPasswordException, RemoteException;

    void createUser(final String login,
                    final String password,
                    final Credentials credentials)
            throws EntryRedefinitionException, RemoteException;

    List<String> getPublisherNames() throws RemoteException;

    Publisher getPublisher(final String name)
            throws EntryNotFoundException, RemoteException;

    void deletePublisher(final Publisher pub) throws RemoteException;

    void createPublisher(final String name) throws RemoteException;

    void updatePublisher(final Publisher pub) throws RemoteException;

    void createBook(final Book book, int amount)
            throws EntryRedefinitionException, RemoteException;

    Book parseBook(final String name, final String author, final String genre,
                   final String pubName, final String isbn, final String price,
                   final GregorianCalendar date, final String amount)
            throws BookParseException, RemoteException;

    List<Book> getBooks(final String search) throws RemoteException;

    Book getBook(final String isbn) throws RemoteException;

    int getAmountOfBook(final Book book) throws EntryNotFoundException, RemoteException;

    void setAmountOfBook(final Book book, int amount) throws RemoteException;

    void deleteBook(final Book book) throws RemoteException;

    void updateBook(final Book book) throws RemoteException;

    int getNumOfSoldBooks(final Book book) throws EntryNotFoundException, RemoteException;

    void createOrder(final Customer user) throws RemoteException;
}
