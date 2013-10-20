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
        name = volumeInfo.getString("title");
        final JSONArray array = volumeInfo.getJSONArray("authors");
        author = array.getString(0);
        if (volumeInfo.has("publisher")) {
            publisher = volumeInfo.getString("publisher");
        } else {
            publisher = "";
        } 
        if (volumeInfo.has("publishedDate")) {
            publishedDate = volumeInfo.getString("publishedDate");
        } else {
            publishedDate = "";
        }
        if (volumeInfo.has("averageRating")) {
            rating = volumeInfo.getDouble("averageRating");
        } else {
            rating = 0;
        }
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
