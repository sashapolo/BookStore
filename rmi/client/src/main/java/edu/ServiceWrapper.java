package edu;

import edu.rmi.BookStoreService;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * Created with IntelliJ IDEA.
 * User: alexander
 * Date: 10/22/13
 * Time: 11:46 PM
 * To change this template use File | Settings | File Templates.
 */
public final class ServiceWrapper {
    private static BookStoreService service;

    private ServiceWrapper() {
    }

    public static void createBookStoreService(final String hostname) throws RemoteException, NotBoundException {
        final String name = "BookStoreService";
        final Registry registry = LocateRegistry.getRegistry(hostname);
        service = (BookStoreService) registry.lookup(name);
    }

    public static BookStoreService getBookStoreService() {
        if (service == null) {
            throw new NullPointerException("Uninitialized service!");
        }
        return service;
    }
}
