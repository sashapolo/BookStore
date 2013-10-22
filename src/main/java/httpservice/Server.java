/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package httpservice;

import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

/**
 *
 * @author alexander
 */
public final class Server {
    private final HttpServer server;
    
    public Server() throws IOException{
        server = HttpServer.create(new InetSocketAddress(9_999), 0);
        final HttpContext context = server.createContext("/applications/bookstore", new Handler());
        context.getFilters().add(new ParametersFilter());
        server.setExecutor(null);
    }

    public void start() {
        server.start();
    }
    
    public void stop() {
        server.stop(0);
    }
}
