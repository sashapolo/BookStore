/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package httpservice;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author alexander
 */
public final class JsonBookObject {
    private final String name;
    private final String author;
    private final String publisher;
    private final String publishedDate;
    private final double rating;
    
    public JsonBookObject(final JSONObject object) throws JSONException {
        final JSONArray items = object.getJSONArray("items");
        final JSONObject item = items.getJSONObject(0);
        final JSONObject volumeInfo = item.getJSONObject("volumeInfo");
        final JSONArray array = volumeInfo.getJSONArray("authors");

        name = volumeInfo.getString("title");
        author = array.getString(0);
        publisher = volumeInfo.has("publisher") ? volumeInfo.getString("publisher") : "";
        publishedDate = volumeInfo.has("publishedDate") ? volumeInfo.getString("publishedDate") : "";
        rating = volumeInfo.has("averageRating") ? volumeInfo.getDouble("averageRating") : 0;
    }

    public String getName() {
        return name;
    }

    public String getAuthor() {
        return author;
    }

    public String getPublisher() {
        return publisher;
    }

    public String getPublishedDate() {
        return publishedDate;
    }

    public double getRating() {
        return rating;
    }
}
