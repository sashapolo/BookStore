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
    private final String name_;
    private final String genre_;
    private final Publisher publisher_;
    private final GregorianCalendar publicationDate_;
    private final Isbn13 isbn13_;
    private BigDecimal price_;
    private Discount discount_;

    public Book(String name, String genre, Publisher publisher, GregorianCalendar publicationDate,
            Isbn isbn, BigDecimal price, int discount) {
        assert (name != null);
        assert (genre != null);
        assert (publisher != null);
        assert (publicationDate != null);
        assert (price != null);
        assert (isbn != null);

        name_ = name;
        genre_ = genre;
        publisher_ = publisher;
        publicationDate_ = publicationDate;
        isbn13_ = isbn.toIsbn13();
        price_ = price;
        discount_ = new Discount(discount);
    }
    
    public boolean matchesString(String match) {
    	return name_.contains(match) || 
    		   genre_.contains(match) || 
    		   publisher_.getName().contains(match);   	
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
        return publicationDate_;
    }

    public Isbn getIsbn() {
        return isbn13_;
    }
    
    public void setPrice(BigDecimal price) {
    	price_ = price;
    }

    @Override
    public int hashCode() {
    	return isbn13_.hashCode();
    }
    
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Book other = (Book) obj;
		if (isbn13_ == other.isbn13_) {
			return true;
		}
		return false;
	}
}
