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
public class JsonBookObject {
    private final String name_;
    private final String author_;
    private final String publisher_;
    private final String publishedDate_;
    private final double rating_;
    
    public JsonBookObject(final JSONObject object) throws JSONException {
        final JSONArray items = object.getJSONArray("items");
        final JSONObject item = items.getJSONObject(0);
        final JSONObject volumeInfo = item.getJSONObject("volumeInfo");
        name_ = volumeInfo.getString("title");
        final JSONArray array = volumeInfo.getJSONArray("authors");
        author_ = array.getString(0);
        if (volumeInfo.has("publisher")) {
            publisher_ = volumeInfo.getString("publisher");
        } else {
            publisher_ = "";
        } 
        if (volumeInfo.has("publishedDate")) {
            publishedDate_ = volumeInfo.getString("publishedDate");
        } else {
            publishedDate_ = "";
        }
        if (volumeInfo.has("averageRating")) {
            rating_ = volumeInfo.getDouble("averageRating");
        } else {
            rating_ = 0;
        }
    }

    public String getName() {
        return name_;
    }

    public String getAuthor() {
        return author_;
    }

    public String getPublisher() {
        return publisher_;
    }

    public String getPublishedDate() {
        return publishedDate_;
    }

    public double getRating() {
        return rating_;
    }
}
