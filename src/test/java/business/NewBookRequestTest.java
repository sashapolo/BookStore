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
	private Book book;
	private Request request;

	public NewBookRequestTest() {
		BookCatalogue.INSTANCE.clear();
		Stock.INSTANCE.clear();
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
