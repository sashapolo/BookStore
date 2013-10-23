/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package httpservice;

import com.sun.net.httpserver.Filter;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLDecoder;
import java.util.*;
import java.util.regex.Pattern;

/**
 *
 * @author alexander
 */
final class ParametersFilter extends Filter {

    private static final Pattern AMPERS = Pattern.compile("[&]");
    private static final Pattern EQ = Pattern.compile("[=]");

    @Override
    public void doFilter(final HttpExchange he, final Chain chain) throws IOException {
        parseParameters(he);
        chain.doFilter(he);
    }

    @Override
    public String description() {
        return "parameters parser";
    }
    
    private static void parseParameters(final HttpExchange he) throws UnsupportedEncodingException {
        final URI requestedUri = he.getRequestURI();
        final String query = requestedUri.getRawQuery();
        he.setAttribute("parameters", parseQuery(query));
    }
    
    @SuppressWarnings("unchecked")
    private static Map<String, Object> parseQuery(final String query) throws UnsupportedEncodingException {
        final Map<String, Object> result = new HashMap<>();
        if (query == null) return result;
        
        final String[] pairs = AMPERS.split(query);
        for (final String pair : pairs) {
            final String[] params = EQ.split(pair);

            String key = null;
            String value = null;
            if (params.length > 0) {
                key = URLDecoder.decode(params[0], System.getProperty("file.encoding"));
            }
            if (params.length > 1) {
                value = URLDecoder.decode(params[1], System.getProperty("file.encoding"));
            }

            if (result.containsKey(key)) {
                final Object obj = result.get(key);
                if (obj instanceof List<?>) {
                    ((Collection<String>) obj).add(value);
                } else if (obj instanceof String) {
                    final Collection<String> values = new ArrayList<>();
                    values.add((String) obj);
                    values.add(value);
                    result.put(key, values);
                }
            } else {
                result.put(key, value);
            }
        }
        return result;
    }
}
