package business;

import static org.junit.Assert.assertEquals;

import java.util.GregorianCalendar;

import org.junit.Test;


public class CartTest {
    private final Book book;
    public static final double EPSILON = 1e-15;

    public CartTest() {
        book = new Book(0,
                        "",
                        "", 
                        new Publisher(0, "", "", "", ""),
                        new GregorianCalendar(), 
                        new Isbn13("9783161484100"), 
                        120.44,
                        50);
    }

    @Test
    public void testCartGetPrice1() {
        final Cart tester = new Cart();
        tester.put(new OrderEntry(book, 2));
        tester.put(new OrderEntry(book, 2));
        tester.put(new OrderEntry(book, 2));
        assertEquals("Incorrect cart size after inserting identical books", 1, tester.size());
        assertEquals("Incorrect cart price",
                     120.44 * 0.5 * 6 * 0.8,
                     tester.getPrice(new Discount(20)),
                     EPSILON);
    }

    @Test
    public void testEmptyCartGetPrice() {
        final Cart tester = new Cart();
        assertEquals("Non zero price of an empty cart",
                     0,
                     tester.getPrice(new Discount(0)),
                     EPSILON);
    }
}
