package edu;

import edu.httpservice.Server;
import rmi.BookStoreService;
import rmi.ServiceFacade;

import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: alexander
 * Date: 5/26/13
 * Time: 3:36 PM
 * To change this template use File | Settings | File Templates.
 */
public final class Main {
    private static final Server SERVER;
    private static final Logger LOGGER = Logger.getLogger(Main.class.getName());

    static {
       try {
           SERVER = new Server();
       } catch (IOException e) {
           LOGGER.log(Level.SEVERE, null, e);
           throw new IllegalStateException();
       }
    }

    private Main() {
    }

    public static void main(final String[] args) {
        SERVER.start();

        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        }
        try {
            final String name = "BookStoreService";
            final BookStoreService service = new ServiceFacade();
            final BookStoreService stub =
                    (BookStoreService) UnicastRemoteObject.exportObject(service, 0);
            final Registry registry = LocateRegistry.getRegistry();
            registry.rebind(name, stub);

            LOGGER.info("BookStoreService bound");
        } catch (RemoteException e) {
            LOGGER.log(Level.SEVERE, null, e);
        }
    }
    
    public static void exit() {
        SERVER.stop();
    }
}
