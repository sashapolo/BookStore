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
    public ExpectedException exception = ExpectedException.none();

    @Before
    public void init() {
        book = new Book("", "", new Publisher(0, "", ""), new GregorianCalendar(), new Isbn13(
                "9783161484100"), new BigDecimal(120.44), 50);
    }

    @Test
    public void testOrderEntryGetPrice1() {
        OrderEntry tester = new OrderEntry(book, 5);
        assertEquals(
                BigDecimal.valueOf(120.44).multiply(BigDecimal.valueOf(0.5))
                        .multiply(BigDecimal.valueOf(5)).setScale(2, BigDecimal.ROUND_HALF_UP),
                tester.getDisplayedPrice());
    }

    @Test
    public void testOrderEntryIllegalArg() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("Amount must be of positive value");
        @SuppressWarnings("unused")
        OrderEntry tester = new OrderEntry(book, -2);
    }
}
