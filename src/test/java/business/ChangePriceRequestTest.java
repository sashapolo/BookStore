package business;

import business.requests.ChangePriceRequest;
import business.requests.Request;
import dbwrappers.BookCatalogue;
import dbwrappers.Stock;
import org.junit.Test;

import java.util.GregorianCalendar;

import static org.junit.Assert.assertEquals;

public class ChangePriceRequestTest {
    public static final double EPSILON = 1e-15;
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
		                120.44,
		                0);
		BookCatalogue.INSTANCE.addBook(book);
	}
	
	@Test
	public void testApprove() {
		final Request request  = new ChangePriceRequest(pub,
                                                        new Isbn13("9783161484100"),
                                                        69);
		request.approve();
		assertEquals("Size of catalogue changed", 1, BookCatalogue.INSTANCE.size());
		assertEquals("Request was not approved", Request.RequestStatus.APPROVED, request.getStatus());
		assertEquals("Price didn't change after approval",
                     BookCatalogue.INSTANCE.getBook(book.getIsbn()).getPrice(),
					 69,
                     EPSILON);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testApproveNonExisting() {
		final Request request  = new ChangePriceRequest(pub,
                                                        new Isbn10("097522980X").toIsbn13(),
				                                        69);
		request.approve();
	}
}
