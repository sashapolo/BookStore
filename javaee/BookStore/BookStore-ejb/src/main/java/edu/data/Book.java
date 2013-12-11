/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.data;

import java.io.Serializable;
import java.util.Calendar;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import org.hibernate.validator.constraints.NotEmpty;

/**
 *
 * @author alexander
 */
@Entity
@NamedQueries({
    @NamedQuery(name = Book.FIND_ALL, query = "SELECT b FROM Book b"),
    @NamedQuery(name = Book.FIND_BY_TITLE, 
            query = "SELECT b FROM Book b WHERE b.title = :title"),
    @NamedQuery(name = Book.FIND_BY_ISBN, 
            query = "SELECT b FROM Book b WHERE b.isbn = :isbn"),
    @NamedQuery(name = Book.FUZZY_FIND,
            query = "SELECT b FROM Book b WHERE b.title LIKE :str")
})
public class Book implements Serializable {
    private static final long serialVersionUID = 1L;
    public static final String FIND_ALL = "Book.findAll";
    public static final String FIND_BY_TITLE = "Book.findByTitle";
    public static final String FIND_BY_ISBN = "Book.findByIsbn";
    public static final String FUZZY_FIND = "Book.fuzzyFind";
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Embedded 
    @NotNull @Valid @Column(unique = true)
    private Isbn isbn;
    @NotEmpty
    private String title;
    @OneToOne
    @NotNull 
    private Author author;
    @NotNull
    private String genre;
    @OneToOne
    private Publisher publisher;
    @Temporal(javax.persistence.TemporalType.DATE)
    @Past
    private Calendar publicationDate;
    @Min(0)
    private double price;
    @NotNull @Valid
    private Discount discount;
    @NotNull
    private String description;

    protected Book() {}
    
    private Book(Builder builder) {
        isbn = builder.isbn;
        title = builder.title;
        author = builder.author;
        genre = builder.genre;
        publisher = builder.publisher;
        publicationDate = builder.publicationDate;
        price = builder.price;
        discount = builder.discount;
        description = builder.description;
    }
    
    public static class Builder {
        private final Isbn isbn;
        private final String title;
        private final Author author;
        private String genre = "";
        private Publisher publisher = null;
        private Calendar publicationDate = null;
        private double price = 0;
        private Discount discount = new Discount();
        private String description = "";
        
        public Builder(final Isbn isbn, final String title, final Author author) {
            this.isbn = isbn;
            this.title = title;
            this.author = author;
        }
        
        public Builder genre(final String genre) {
            this.genre = genre;
            return this;
        }
        
        public Builder publisher(final Publisher publisher) {
            this.publisher = publisher;
            return this;
        }
        
        public Builder publicationDate(final Calendar date) {
            this.publicationDate = (Calendar) date.clone();
            return this;
        } 
        
        public Builder price(final double price) {
            this.price = price;
            return this;
        }
        
        public Builder discount(final Discount discount) {
            this.discount = discount;
            return this;
        }
        
        public Builder description(final String description) {
            this.description = description;
            return this;
        }
        
        public Book build() {
            return new Book(this);
        }
    }
    
    public Long getId() {
        return id;
    }

    public Isbn getIsbn() {
        return isbn;
    }

    public String getTitle() {
        return title;
    }

    public Author getAuthor() {
        return author;
    }

    public String getGenre() {
        return genre;
    }

    public Publisher getPublisher() {
        return publisher;
    }

    public Calendar getPublicationDate() {
        return (Calendar) publicationDate.clone();
    }

    public double getPrice() {
        return discount.apply(price);
    }

    public Discount getDiscount() {
        return discount;
    }

    public String getDescription() {
        return description;
    }
}
