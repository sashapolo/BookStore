package business;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.GregorianCalendar;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;


public class BookTest {

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void testBookGetPrice1() {
        Book tester = new Book("", 
                               "", 
                               new Publisher("", ""), 
                               new GregorianCalendar(),
                               new Isbn13("9783161484100"), 
                               new BigDecimal(120.44), 
                               0);
        assertEquals(BigDecimal.valueOf(120.44).setScale(2, BigDecimal.ROUND_HALF_UP),
                tester.getDisplayedPrice());
    }

    @Test
    public void testBookGetPrice2() {
        Book tester = new Book("", 
                               "", 
                               new Publisher("", ""), 
                               new GregorianCalendar(),
                               new Isbn13("9783161484100"), 
                               new BigDecimal(120.44), 
                               100);
        assertEquals(BigDecimal.valueOf(0).setScale(2, BigDecimal.ROUND_HALF_UP),
                tester.getDisplayedPrice());
    }

    @Test
    public void testBookGetPrice3() {
        Book tester = new Book("", 
                               "", 
                               new Publisher("", ""), 
                               new GregorianCalendar(),
                               new Isbn13("9783161484100"), 
                               new BigDecimal(120.44), 
                               48);
        assertEquals(
                BigDecimal.valueOf(120.44).multiply(BigDecimal.valueOf(0.52))
                        .setScale(2, BigDecimal.ROUND_HALF_UP), tester.getDisplayedPrice());
    }

    @Test
    public void testBookIllegalArg() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("Illegal discount settings");
        @SuppressWarnings("unused")
        Book tester = new Book("", 
                               "", 
                               new Publisher("", ""), 
                               new GregorianCalendar(),
                               new Isbn13("9783161484100"), 
                               new BigDecimal(120.44), 
                               -1);
    }
}
