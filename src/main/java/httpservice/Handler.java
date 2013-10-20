/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package httpservice;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.json.JSONObject;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

/**
 *
 * @author alexander
 */
final class Handler implements HttpHandler {
    @Override
    public void handle(HttpExchange he) throws IOException {
        @SuppressWarnings("unchecked")
        Map<String, Object> params = (Map<String, Object>) he.getAttribute("parameters");
        try (OutputStream ostream = he.getResponseBody()) {
            if (!params.containsKey("isbn")) {
                String response = "Wrong parameters. Need parameter isbn=<string>.";
                he.sendResponseHeaders(400, response.length()); 
                ostream.write(response.getBytes());
                return;
            }
            
            final String isbn = (String) params.get("isbn");
            JSONObject object = JsonBookMapper.mapNumSold(isbn);
            final String response = object.toString();
            he.sendResponseHeaders(200, response.length());
            ostream.write(response.getBytes());
        }
    }
}
