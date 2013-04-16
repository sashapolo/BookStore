package business;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.GregorianCalendar;

import org.junit.Test;

import business.requests.ChangePriceRequest;
import business.requests.Request;
import dbwrappers.BookCatalogue;
import dbwrappers.Stock;

public class ChangePriceRequestTest {
	private final Book book;
	private final Publisher pub;

	public ChangePriceRequestTest() {
		BookCatalogue.INSTANCE.clear();
		Stock.INSTANCE.clear();
		pub = new Publisher(0, "", "", "foo", "bar");
		book = new Book(0,
                        "",
		                "", 
		                new Publisher(0, "", "", "", ""),
		                new GregorianCalendar(), 
		                new Isbn13("9783161484100"), 
		                new BigDecimal(120.44), 
		                0);
		BookCatalogue.INSTANCE.addBook(book);
	}
	
	@Test
	public void testApprove() {
		final Request request  = new ChangePriceRequest(pub,
                                                        new Isbn13("9783161484100"),
                                                        BigDecimal.valueOf(69));
		request.approve();
		assertEquals("Size of catalogue changed", 1, BookCatalogue.INSTANCE.size());
		assertEquals("Request was not approved", Request.RequestStatus.APPROVED, request.getStatus());
		assertEquals("Price didn't change after approval",
                     BookCatalogue.INSTANCE.getBook(book.getIsbn()).getDisplayedPrice(),
					 BigDecimal.valueOf(69).setScale(2, BigDecimal.ROUND_HALF_UP));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testApproveNonExisting() {
		final Request request  = new ChangePriceRequest(pub,
                                                        new Isbn10("097522980X").toIsbn13(),
				                                        BigDecimal.valueOf(69));
		request.approve();
	}
}
