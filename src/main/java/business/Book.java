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

	public Book(String name, String genre, Publisher publisher,
			GregorianCalendar publicationDate, Isbn13 isbn13, BigDecimal price,
			int discount) {
		assert (name != null);
		assert (genre != null);
		assert (publisher != null);
		assert (publicationDate != null);
		assert (price != null);
		assert (isbn13 != null);

		name_ = name;
		genre_ = genre;
		publisher_ = publisher;
		publicationDate_ = publicationDate;
		isbn13_ = isbn13;
		price_ = price;
		discount_ = new Discount(discount);
	}

	public Book(String name, String genre, Publisher publisher,
			GregorianCalendar publicationDate, Isbn10 isbn10, BigDecimal price,
			int discount) {
		this(name, genre, publisher, publicationDate, new Isbn13(isbn10),
				price, discount);
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

	public Isbn13 getIsbn() {
		return isbn13_;
	}
}
