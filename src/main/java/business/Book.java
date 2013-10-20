/**
 * 
 */
package business;

import java.util.GregorianCalendar;


/**
 * @author alexander
 * 
 */
public final class Book {
    private final Builder builder;

    private Book(final Builder builder) {
        assert (builder != null);
        this.builder = builder;
    }
    
    public boolean matchesString(final String match) {
    	return builder.name.contains(match) ||
               builder.genre.contains(match) ||
               builder.publisher.getName().contains(match);
    }

    public int getId() {
        return builder.id;
    }

    public double getPrice() {
        return builder.price * builder.discount.getInvertedValue();
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
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof Book)) return false;

        final Book book = (Book) o;
        return builder.isbn.equals(book.builder.isbn);
    }

    public static final class Builder {
        private int id = 0;
        private final String name;
        private final String author;
        private final String genre;
        private final Publisher publisher;
        private final GregorianCalendar publicationDate;
        private final Isbn isbn;
        private double price;
        private Discount discount = new Discount(0);

        public Builder(final String name,
                       final String author,
                       final String genre,
                       final Publisher publisher,
                       final GregorianCalendar publicationDate,
                       final Isbn isbn,
                       final double price) {
            assert (name != null);
            assert (author != null);
            assert (genre != null);
            assert (publisher != null);
            assert (publicationDate != null);
            assert (isbn != null);
            
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
            assert (discount != null);
            this.discount = discount;
            return this;
        }

        public Book build() {
            return new Book(this);
        }
    }
}
