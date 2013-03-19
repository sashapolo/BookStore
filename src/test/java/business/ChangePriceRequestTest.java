package business;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.GregorianCalendar;

import org.junit.Test;
import dbwrappers.BookCatalogue;

public class ChangePriceRequestTest {
	private Book book;
	private Publisher pub;

	public ChangePriceRequestTest() {
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
		Request request  = new ChangePriceRequest(pub, new Isbn13("9783161484100"), BigDecimal.valueOf(69));
		request.approve();
		assertEquals(BookCatalogue.INSTANCE.size(), 1);
		assertEquals(request.getStatus(), Request.RequestStatus.APPROVED);
		assertEquals(BookCatalogue.INSTANCE.getBook(book.getIsbn()).getDisplayedPrice(), 
					 BigDecimal.valueOf(69).setScale(2, BigDecimal.ROUND_HALF_UP));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testApproveNonExisting() {
		Request request  = new ChangePriceRequest(pub, new Isbn10("097522980X").toIsbn13(), 
				BigDecimal.valueOf(69));
		request.approve();
	}
}
