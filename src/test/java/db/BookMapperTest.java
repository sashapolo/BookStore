package db;

import business.Book;
import business.Isbn13;
import business.Publisher;
import junit.framework.Test;

import java.util.GregorianCalendar;

/**
 * Created with IntelliJ IDEA.
 * User: alexander
 * Date: 5/14/13
 * Time: 9:15 PM
 * To change this template use File | Settings | File Templates.
 */
public class BookMapperTest {

    @Test
    public void NormalInsertTest() {
        ConnectionManager manager = new TestConnectionManager();
        BookMapper mapper = new BookMapper(manager);
        Book book = new Book.Builder(0,
                "",
                "",
                new Publisher(0, "", 0, "", "", ""),
                new GregorianCalendar(),
                new Isbn13("9783161484100"),
                120.44).build();
        try {

        }
    }
}
