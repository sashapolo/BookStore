package business;

import org.junit.Test;

import java.util.GregorianCalendar;

import static org.junit.Assert.assertEquals;


public final class CartTest {
    private static final double EPSILON = 1e-15;
    private final Book book;

    public CartTest() throws WrongFormatException {
        book = new Book.Builder("",
                                "",
                                "",
                                new Publisher(0, ""),
                                new GregorianCalendar(),
                                new Isbn("9783161484100"),
                                120.44).discount(50).build();
    }

    @Test
    public void testCartGetPrice1() {
        final Cart tester = new Cart();
        tester.put(new OrderEntry(0, book, 2));
        tester.put(new OrderEntry(0, book, 2));
        tester.put(new OrderEntry(0, book, 2));
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
