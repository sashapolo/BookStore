/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package httpservice;

import business.Book;
import org.json.JSONObject;
import service.EntryNotFoundException;
import service.ServiceFacade;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author alexander
 */
class JsonBookMapper {
    public static JSONObject mapNumSold(final String isbn) {
        try {
            Map<String, Integer> map = new HashMap<>(1);
            final Book book = ServiceFacade.getBook(isbn);
            map.put("numSold", ServiceFacade.getNumOfSoldBooks(book));
            return new JSONObject(map);
        } catch (EntryNotFoundException ex) {
            Map<String, String> map = new HashMap<>(1);
            map.put("error", ex.getMessage());
            return new JSONObject(map);
        } 
    }

    private JsonBookMapper() {}
}
