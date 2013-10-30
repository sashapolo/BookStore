package edu;

import edu.service.ServiceFacade;
import edu.rmi.BookStoreService;

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
//    private static Server SERVER = null;
    private static final Logger LOGGER = Logger.getLogger(Main.class.getName());
//
//    static {
//       try {
//           SERVER = new Server();
//       } catch (IOException e) {
//           LOGGER.log(Level.SEVERE, null, e);
//       }
//    }

    private Main() {
    }

    public static void main(final String[] args) {
//        if (SERVER != null) SERVER.start();

        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        }
        try {
            final BookStoreService service = new ServiceFacade();
            final BookStoreService stub =
                    (BookStoreService) UnicastRemoteObject.exportObject(service, 0);
            final Registry registry = LocateRegistry.getRegistry();
            final String name = "BookStoreService";
            registry.rebind(name, stub);

            LOGGER.info("BookStoreService bound");
//            if (SERVER != null) SERVER.stop();
        } catch (RemoteException e) {
            LOGGER.log(Level.SEVERE, null, e);
        }
    }
}
