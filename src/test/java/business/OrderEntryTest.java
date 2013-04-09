package business;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.GregorianCalendar;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;


public class OrderEntryTest {
    private Book book;

    @Rule
    private final ExpectedException exception = ExpectedException.none();

    @Before
    public void init() {
        book = new Book("",
                        "",
                        new Publisher("", ""),
                        new GregorianCalendar(),
        				new Isbn13("9783161484100"),
                        new BigDecimal(120.44), 50);
    }

    @Test
    public void testOrderEntryGetPrice1() {
        final OrderEntry tester = new OrderEntry(book, 5);
        assertEquals("Incorrect entry price",
                     BigDecimal.valueOf(120.44).multiply(BigDecimal.valueOf(0.5))
                          .multiply(BigDecimal.valueOf(5))
                          .setScale(2, BigDecimal.ROUND_HALF_UP),
                     tester.getDisplayedPrice());
    }

    @Test
    public void testOrderEntryIllegalArg() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("Amount must be of positive value");
        @SuppressWarnings("unused")
        final OrderEntry tester = new OrderEntry(book, -2);
    }
}
