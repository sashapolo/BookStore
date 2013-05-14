package business;

import business.requests.AddBooksRequest;
import business.requests.Request;
import dbwrappers.BookCatalogue;
import dbwrappers.Stock;
import org.junit.Test;

import java.util.GregorianCalendar;

import static org.junit.Assert.assertEquals;

public class AddBooksRequestTest {
	private final Publisher pub;
	private final Book book;
	
	public AddBooksRequestTest() {
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
		assertEquals("Stock is non-empty", 0, Stock.INSTANCE.get(book.getIsbn()).intValue());
		final Request request  = new AddBooksRequest(pub, book.getIsbn(), 69);
		request.approve();
		assertEquals("Request not approved after approve() call",
                     Request.RequestStatus.APPROVED,
                     request.getStatus());
		assertEquals("Wrong number of books added after",
                     69,
                     Stock.INSTANCE.get(book.getIsbn()).intValue());
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testApproveNonExisting() {
		final Request request  = new AddBooksRequest(pub, new Isbn10("097522980X").toIsbn13(), 69);
		request.approve();
	}
}
