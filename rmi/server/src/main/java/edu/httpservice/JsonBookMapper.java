/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.httpservice;

import edu.business.Book;
import edu.service.ServiceFacade;
import edu.exception.EntryNotFoundException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author alexander
 */
final class JsonBookMapper {
    public static JSONObject mapNumSold(final String isbn) {
        try {
            final Map<String, Integer> map = new HashMap<>(1);
            final ServiceFacade service = new ServiceFacade();
            final Book book = service.getBook(isbn);
            map.put("numSold", service.getNumOfSoldBooks(book));
            return new JSONObject(map);
        } catch (EntryNotFoundException ex) {
            final Map<String, String> map = new HashMap<>(1);
            map.put("error", ex.getMessage());
            return new JSONObject(map);
        } 
    }

    private JsonBookMapper() {}
}
