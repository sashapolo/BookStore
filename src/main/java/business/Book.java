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
	private String name_;
	private String genre_;
	private Publisher publisher_;
	private GregorianCalendar dateOfPublish_;
	private Isbn13 isbn13_;
	private Isbn10 isbn10_;
	private BigDecimal price_;
	private int discount_;
	
	public Book(final String name, final String genre, final Publisher publisher,
			 final GregorianCalendar dateOfPublish, final Isbn13 isbn13, 
			final Isbn10 isbn10,  final BigDecimal price, int discount) {
		
		assert(name != null);
		assert(genre != null);
		assert(publisher != null);
		assert(dateOfPublish != null);
		assert(price != null);
		
		if (discount > 100 || discount < 0) {
			throw new IllegalArgumentException("Illegal discount settings");
		}
		name_ = name;
		genre_ = genre;
		publisher_ = publisher;
		dateOfPublish_ = dateOfPublish;
		isbn13_ = isbn13;
		isbn10_ = isbn10;
		price_ = price;
		discount_ = discount;
	}
	
	public BigDecimal getPrice() {
		BigDecimal newPrice = 
				BigDecimal.valueOf(discount_).divide(BigDecimal.valueOf(100), 2, BigDecimal.ROUND_HALF_UP);
		return price_.subtract(price_.multiply(newPrice));
	}
	
	
}
