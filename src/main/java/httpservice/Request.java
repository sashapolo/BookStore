/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package httpservice;

import business.Isbn;
import java.net.MalformedURLException;
import java.net.URL;

/**
 *
 * @author alexander
 */
public class Request {
    private static final String requestString = "https://www.googleapis.com/books/v1/volumes?q=isbn:";
    private final Isbn isbn_;
    
    public Request(final Isbn isbn) {
        assert (isbn != null);
        isbn_ = isbn;
    }
    
    public URL getUrl() throws MalformedURLException {
        return new URL(requestString + isbn_.toString());
    }
}
