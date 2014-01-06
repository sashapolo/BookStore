/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.data;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Objects;
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
            query = "SELECT b FROM Book b WHERE UPPER(b.title) LIKE :str")
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
    private Calendar publicationDate;
    @Min(0)
    private double price;
    @NotNull @Valid
    private Discount discount;
    @NotNull
    private String description;

    protected Book() {
    }
    
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
        private Isbn isbn;
        private String title;
        private Author author;
        private String genre = "";
        private Publisher publisher;
        private Calendar publicationDate = Calendar.getInstance();
        private double price = 0;
        private Discount discount = new Discount();
        private String description = "";
        
        public Builder isbn(final Isbn isbn) {
            this.isbn = isbn;
            return this;
        }
        
        public Builder title(final String title) {
            this.title = title;
            return this;
        }
        
        public Builder author(final Author author) {
            this.author = author;
            return this;
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

    public void setId(final Long id) {
        this.id = id;
    }
    
    public void setIsbn(Isbn isbn) {
        this.isbn = isbn;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public void setPublisher(Publisher publisher) {
        this.publisher = publisher;
    }

    public void setPublicationDate(Calendar publicationDate) {
        this.publicationDate = (Calendar) publicationDate.clone();
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setDiscount(Discount discount) {
        this.discount = discount;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + Objects.hashCode(this.isbn);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Book other = (Book) obj;
        if (!Objects.equals(this.isbn, other.isbn)) {
            return false;
        }
        return true;
    }
    
    @Override
    public String toString() {
        return "Book{" + "id=" + id + ", isbn=" + isbn + ", title=" + title + ", author=" + author 
                + ", genre=" + genre + ", publisher=" + publisher + ", publicationDate=" 
                + publicationDate.getTime() + ", price=" + price + ", discount=" + discount + '}';
    }
}
