/**
 * 
 */
package business;

import java.math.BigDecimal;
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
    private BigDecimal price_;
    private final Discount discount_;
    private int numSold_;

    public Book(final int id,
                final String name,
                final String genre,
                final Publisher publisher,
                final GregorianCalendar publicationDate,
                final Isbn isbn,
                final BigDecimal price,
                final int discount) {
        assert (name != null);
        assert (genre != null);
        assert (publisher != null);
        assert (publicationDate != null);
        assert (price != null);
        assert (isbn != null);

        id_ = id;
        name_ = name;
        genre_ = genre;
        publisher_ = publisher;
        publicationDate_ = publicationDate;
        isbn13_ = isbn.toIsbn13();
        price_ = price;
        discount_ = new Discount(discount);
    }
    
    public boolean matchesString(final String match) {
    	return name_.contains(match) || 
    		   genre_.contains(match) || 
    		   publisher_.getName().contains(match);   	
    }

    public int getId() {
        return id_;
    }

    public BigDecimal getPrice() {
        return price_.multiply(discount_.getInverted());
    }

    public BigDecimal getDisplayedPrice() {
        return getPrice().setScale(2, BigDecimal.ROUND_HALF_UP);
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
    
    public void setPrice(final BigDecimal price) {
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
