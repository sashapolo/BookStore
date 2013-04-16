package business;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.util.GregorianCalendar;

import org.junit.Test;

import business.requests.NewBookRequest;
import business.requests.Request;

import dbwrappers.BookCatalogue;
import dbwrappers.Stock;

public class NewBookRequestTest {
	private final Book book;
	private final Request request;

	public NewBookRequestTest() {
		BookCatalogue.INSTANCE.clear();
		Stock.INSTANCE.clear();
		Publisher pub = new Publisher(0, "", "", "foo", "bar");
		book = new Book(0,
                        "",
		                "", 
		                new Publisher(0, "", "", "", ""),
		                new GregorianCalendar(), 
		                new Isbn13("9783161484100"), 
		                new BigDecimal(120.44), 
		                50);
		request  = new NewBookRequest(pub, book);
	}
	
	@Test
	public void testApprove() {
		request.approve();
		assertEquals("Catalogue size didn't change after approval", 1, BookCatalogue.INSTANCE.size());
		assertEquals("Incorrect request status",
                     Request.RequestStatus.APPROVED,
                     request.getStatus());
		assertEquals("Incorrect book in the catalogue after approval",
                     book,
                     BookCatalogue.INSTANCE.getBook(book.getIsbn()));
	}
}
