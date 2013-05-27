/**
 * 
 */
package business;

import java.util.GregorianCalendar;


/**
 * @author alexander
 * 
 */
public class Book {
    private final Builder builder_;

    public static class Builder {
        private int id_ = 0;
        private final String name_;
        private final String author_;
        private final String genre_;
        private final Publisher publisher_;
        private final GregorianCalendar publicationDate_;
        private final Isbn isbn13_;
        private double price_;
        private Discount discount_ = new Discount(0);
        private int numSold_ = 0;

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
            
            name_ = name;
            author_ = author;
            genre_ = genre;
            publisher_ = publisher;
            publicationDate_ = publicationDate;
            isbn13_ = isbn.toIsbn13();
            price_ = price;
        }

        public Builder id(final int id) {
            id_ = id;
            return this;
        }
        
        public Builder discount(final int discount) {
            discount_ = new Discount(discount);
            return this;
        }

        public Builder discount(final Discount discount) {
            assert (discount != null);
            discount_ = discount;
            return this;
        }

        public Builder numSold(final int numSold) {
            numSold_ = numSold;
            return this;
        }

        public Book build() {
            return new Book(this);
        }
    }

    private Book(final Builder builder) {
        assert (builder != null);
        builder_ = builder;
    }
    
    public boolean matchesString(final String match) {
    	return builder_.name_.contains(match) ||
               builder_.genre_.contains(match) ||
               builder_.publisher_.getName().contains(match);
    }

    public int getId() {
        return builder_.id_;
    }

    public double getPrice() {
        return builder_.price_ * builder_.discount_.getInvertedValue();
    }

    public String getName() {
        return builder_.name_;
    }
    
    public String getAuthor() {
        return builder_.author_;
    }

    public String getGenre() {
        return builder_.genre_;
    }

    public Discount getDiscount() {
        return builder_.discount_;
    }

    public Publisher getPublisher() {
        return builder_.publisher_;
    }

    public GregorianCalendar getPublicationDate() {
        return (GregorianCalendar) builder_.publicationDate_.clone();
    }

    public Isbn getIsbn() {
        return builder_.isbn13_;
    }
    
    public int getNumSold() {
    	return builder_.numSold_;
    }
    
    public void setPrice(final double price) {
        builder_.price_ = price;
    }

    public void setId(final int id) {
        builder_.id_ = id;
    }

    @Override
    public int hashCode() {
    	return builder_.isbn13_.hashCode();
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof Book)) return false;

        final Book book = (Book) o;
        return builder_.isbn13_.equals(book.builder_.isbn13_);
    }
}
