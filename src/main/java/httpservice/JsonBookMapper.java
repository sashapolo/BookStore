/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package httpservice;

import business.Book;
import business.WrongFormatException;
import org.json.JSONObject;
import service.BookCatalogue;
import service.EntryNotFoundException;

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
            final Book book = BookCatalogue.getBook(isbn);
            map.put("numSold", BookCatalogue.getNumSold(book));
            return new JSONObject(map);
        } catch (WrongFormatException | EntryNotFoundException ex) {
            Map<String, String> map = new HashMap<>(1);
            map.put("error", ex.getMessage());
            return new JSONObject(map);
        } 
    }

    private JsonBookMapper() {}
}
