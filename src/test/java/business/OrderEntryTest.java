package business;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.GregorianCalendar;

import static org.junit.Assert.assertEquals;


public class OrderEntryTest {
    private Book book;
    public static final double EPSILON = 1e-15;

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Before
    public void init() {
        book = new Book.Builder(0,
                "",
                "",
                new Publisher(0, "", 0, "", "", ""),
                new GregorianCalendar(),
                new Isbn13("9783161484100"),
                120.44).discount(50).build();
    }

    @Test
    public void testOrderEntryGetPrice1() {
        final OrderEntry tester = new OrderEntry(0, book, 5);
        assertEquals("Incorrect entry price",
                     120.44 * 0.5 * 5,
                     tester.getPrice(),
                     EPSILON);
    }

    @Test
    public void testOrderEntryIllegalArg() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("Amount must be of positive value");
        @SuppressWarnings("unused")
        final OrderEntry tester = new OrderEntry(0, book, -2);
    }
}
