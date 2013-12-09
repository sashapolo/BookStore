/**
 * 
 */
package edu.business;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.GregorianCalendar;


/**
 * @author alexander
 * 
 */
public final class Book implements Serializable {
    private static final long serialVersionUID = 3133657525962185632L;
    private final Builder builder;

    private Book(final Builder builder) {
        assert builder != null;
        this.builder = builder;
    }

    public int getId() {
        return builder.id;
    }

    public double getPrice() {
        return builder.price * builder.discount.invertedValue();
    }

    public String getName() {
        return builder.name;
    }
    
    public String getAuthor() {
        return builder.author;
    }

    public String getGenre() {
        return builder.genre;
    }

    public Discount getDiscount() {
        return builder.discount;
    }

    public Publisher getPublisher() {
        return builder.publisher;
    }

    public GregorianCalendar getPublicationDate() {
        return (GregorianCalendar) builder.publicationDate.clone();
    }

    public Isbn getIsbn() {
        return builder.isbn;
    }
    
    public void setPrice(final double price) {
        builder.price = price;
    }

    public void setId(final int id) {
        builder.id = id;
    }

    @Override
    public int hashCode() {
    	return builder.isbn.hashCode();
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Book)) return false;

        final Book book = (Book) obj;
        return builder.isbn.equals(book.builder.isbn);
    }

    public static final class Builder implements Serializable {
        private static final long serialVersionUID = 3934274355868228594L;
        private int id = -1;
        private final String name;
        private final String author;
        private final String genre;
        private final Publisher publisher;
        private final GregorianCalendar publicationDate;
        private transient Isbn isbn;
        private double price;
        private transient Discount discount = new Discount(0);

        public Builder(final String name, final String author,
                       final String genre, final Publisher publisher,
                       final GregorianCalendar publicationDate, final Isbn isbn,
                       final double price) {
            assert name != null;
            assert author != null;
            assert genre != null;
            assert publisher != null;
            assert publicationDate != null;
            assert isbn != null;
            
            this.name = name;
            this.author = author;
            this.genre = genre;
            this.publisher = publisher;
            this.publicationDate = (GregorianCalendar) publicationDate.clone();
            this.isbn = isbn;
            this.price = price;
        }

        public Builder id(final int id) {
            this.id = id;
            return this;
        }
        
        public Builder discount(final int discount) {
            this.discount = new Discount(discount);
            return this;
        }

        public Builder discount(final Discount discount) {
            assert discount != null;
            this.discount = discount;
            return this;
        }

        public Book build() {
            return new Book(this);
        }

        private void readObject(final ObjectInputStream inputStream)
                throws ClassNotFoundException, IOException {
            inputStream.defaultReadObject();

            isbn = new Isbn((String) inputStream.readObject());
            discount = new Discount(inputStream.readInt());
        }

        private void writeObject(final ObjectOutputStream outputStream) throws IOException {
            outputStream.defaultWriteObject();

            outputStream.writeObject(isbn.toString());
            outputStream.writeInt(discount.integerValue());
        }
    }
}
