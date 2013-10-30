/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.httpservice;

import edu.exception.ServiceException;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

/**
 *
 * @author alexander
 */
public final class GBooksHttpConnector {
    public static JSONObject executeRequest(final Request request) throws ServiceException {
        try {
            final URL url = request.getUrl();
            final URLConnection connection = url.openConnection();
            connection.connect();
            final InputStream stream = connection.getInputStream();
            return new JSONObject(streamToString(stream));
        } catch (IOException | JSONException ex) {
            throw new ServiceException(ex.getMessage());
        }
    }
    
    private static String streamToString(final InputStream stream) throws IOException {
        final StringBuilder stringBuilder = new StringBuilder();
        try (final BufferedReader reader = new BufferedReader(new InputStreamReader(stream))) {
            final String ls = System.getProperty("line.separator");
            String line = reader.readLine();
            while (line != null) {
                stringBuilder.append(line);
                stringBuilder.append(ls);
                line = reader.readLine();
            }
        }
        return stringBuilder.toString();
    }

    private GBooksHttpConnector() {}
}
