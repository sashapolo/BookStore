package business;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.GregorianCalendar;

import org.junit.Test;

import business.requests.AddBooksRequest;
import business.requests.Request;
import dbwrappers.BookCatalogue;
import dbwrappers.Stock;

public class AddBooksRequestTest {
	private Publisher pub;
	private Book book;
	
	public AddBooksRequestTest() {
		BookCatalogue.INSTANCE.clear();
		Stock.INSTANCE.clear();
		pub = new Publisher("foo", "bar");
		book = new Book("", 
		                "", 
		                new Publisher("", ""), 
		                new GregorianCalendar(), 
		                new Isbn13("9783161484100"), 
		                new BigDecimal(120.44), 
		                0);
		BookCatalogue.INSTANCE.addBook(book);
	}
	
	@Test
	public void testApprove() {
		assertEquals(Stock.INSTANCE.get(book.getIsbn()).intValue(), 0);
		Request request  = new AddBooksRequest(pub, book.getIsbn(), 69);
		request.approve();
		assertEquals(request.getStatus(), Request.RequestStatus.APPROVED);
		assertEquals(Stock.INSTANCE.get(book.getIsbn()).intValue(), 69);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testApproveNonExisting() {
		Request request  = new AddBooksRequest(pub, new Isbn10("097522980X").toIsbn13(), 69);
		request.approve();
	}
}
