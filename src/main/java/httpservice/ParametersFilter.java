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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author alexander
 */
public class ParametersFilter extends Filter {

    @Override
    public void doFilter(HttpExchange he, Chain chain) throws IOException {
        parseParameters(he);
        chain.doFilter(he);
    }

    @Override
    public String description() {
        return "parameters parser";
    }
    
    private void parseParameters(final HttpExchange he) throws UnsupportedEncodingException {
        final URI requestedUri = he.getRequestURI();
        final String query = requestedUri.getRawQuery();
        he.setAttribute("parameters", parseQuery(query));
    }
    
    @SuppressWarnings("unchecked")
    private Map<String, Object> parseQuery(final String query) throws UnsupportedEncodingException {
        Map<String, Object> result = new HashMap<>();
        if (query == null) return result;
        
        final String[] pairs = query.split("[&]");
        for (final String pair : pairs) {
            String[] params = pair.split("[=]");
            String key = null;
            String value = null;
            
            if (params.length > 0) {
                key = URLDecoder.decode(params[0], System.getProperty("file.encoding"));
            }
            if (params.length > 1) {
                value = URLDecoder.decode(params[1], System.getProperty("file.encoding"));
            }

            if (result.containsKey(key)) {
                Object obj = result.get(key);
                if (obj instanceof List<?>) {
                    ((List<String>) obj).add(value);
                } else if (obj instanceof String) {
                    List<String> values = new ArrayList<>();
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
