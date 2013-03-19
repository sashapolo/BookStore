package business;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.util.GregorianCalendar;

import org.junit.Test;

import dbwrappers.BookCatalogue;

public class NewBookRequestTest {
	private Book book;
	private Request request;

	public NewBookRequestTest() {
		Publisher pub = new Publisher("foo", "bar");
		book = new Book("", 
		                "", 
		                new Publisher("", ""), 
		                new GregorianCalendar(), 
		                new Isbn13("9783161484100"), 
		                new BigDecimal(120.44), 
		                50);
		request  = new NewBookRequest(pub, book);
	}
	
	@Test
	public void testApprove() {
		request.approve();
		assertEquals(BookCatalogue.INSTANCE.size(), 1);
		assertEquals(request.getStatus(), Request.RequestStatus.APPROVED);
		assertEquals(book, BookCatalogue.INSTANCE.getBook(book.getIsbn()));
	}
}
