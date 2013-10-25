/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package business;

/**
 *
 * @author alexander
 */
public final class GoogleBook {
    private final String name;
    private final String author;
    private final String publisher;
    private final String publishedDate;
    private final double rating;
    
    public GoogleBook(final String name, final String author, final String publisher,
                      final String publishedDate, final double rating) {
        this.name = name;
        this.author = author;
        this.publisher = publisher;
        this.publishedDate = publishedDate;
        this.rating = rating;
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
