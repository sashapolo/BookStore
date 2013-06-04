/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package httpservice;

import business.BookStore;
import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author alexander
 */
public class Server {
    private HttpServer server;
    
    public Server() {
        try {
            server = HttpServer.create(new InetSocketAddress(9_999), 0);
            HttpContext context = server.createContext("/applications/bookstore", new Handler());
            context.getFilters().add(new ParametersFilter());           
            server.setExecutor(null);
        } catch (IOException ex) {
            Logger.getLogger(BookStore.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void start() {
        server.start();
    }
    
    public void stop() {
        server.stop(0);
    }
}
