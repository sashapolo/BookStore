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
    private final int id_;
    private final String name_;
    private final String genre_;
    private final Publisher publisher_;
    private final GregorianCalendar publicationDate_;
    private final Isbn isbn13_;
    private double price_;
    private final Discount discount_;
    private int numSold_;

    public static class Builder {
        private final int id_;
        private final String name_;
        private final String genre_;
        private final Publisher publisher_;
        private final GregorianCalendar publicationDate_;
        private final Isbn isbn13_;
        private double price_;
        private Discount discount_ = new Discount(0);
        private int numSold_ = 0;

        public Builder(int id,
                       final String name,
                       final String genre,
                       final Publisher publisher,
                       final GregorianCalendar publicationDate,
                       final Isbn isbn,
                       double price) {
            assert (name != null);
            assert (genre != null);
            assert (publisher != null);
            assert (publicationDate != null);
            assert (isbn != null);

            id_ = id;
            name_ = name;
            genre_ = genre;
            publisher_ = publisher;
            publicationDate_ = publicationDate;
            isbn13_ = isbn.toIsbn13();
            price_ = price;
        }

        public Builder discount(int discount) {
            discount_ = new Discount(discount);
            return this;
        }

        public Builder discount(final Discount discount) {
            assert (discount != null);
            discount_ = discount;
            return this;
        }

        public Builder numSold(int numSold) {
            numSold_ = numSold;
            return this;
        }

        public Book build() {
            return new Book(this);
        }
    }

    private Book(final Builder builder) {
        assert (builder != null);

        id_ = builder.id_;
        name_ = builder.name_;
        genre_ = builder.genre_;
        publisher_ = builder.publisher_;
        publicationDate_ = builder.publicationDate_;
        isbn13_ = builder.isbn13_;
        discount_ = builder.discount_;
        numSold_ = builder.numSold_;
    }
    
    public boolean matchesString(final String match) {
    	return name_.contains(match) || 
    		   genre_.contains(match) || 
    		   publisher_.getName().contains(match);   	
    }

    public int getId() {
        return id_;
    }

    public double getPrice() {
        return price_ * discount_.getInvertedValue();
    }

    public String getName() {
        return name_;
    }

    public String getGenre() {
        return genre_;
    }

    public Publisher getPublisher() {
        return publisher_;
    }

    public GregorianCalendar getPublicationDate() {
        return (GregorianCalendar) publicationDate_.clone();
    }

    public Isbn getIsbn() {
        return isbn13_;
    }
    
    public int getNumSold() {
    	return numSold_;
    }
    
    public void setPrice(double price) {
    	price_ = price;
    }

    @Override
    public int hashCode() {
    	return isbn13_.hashCode();
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof Book)) return false;

        final Book book = (Book) o;
        return isbn13_.equals(book.isbn13_);
    }
}
