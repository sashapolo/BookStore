package business;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.util.GregorianCalendar;

import org.junit.Test;


public class CartTest {
    private Book book;

    public CartTest() {
        book = new Book("", 
                        "", 
                        new Publisher("", ""), 
                        new GregorianCalendar(), 
                        new Isbn13("9783161484100"), 
                        new BigDecimal(120.44), 
                        50);
    }

    @Test
    public void testCartGetPrice1() {
        Cart tester = new Cart();
        tester.put(new OrderEntry(book, 2));
        tester.put(new OrderEntry(book, 2));
        tester.put(new OrderEntry(book, 2));
        assertEquals(1, tester.size());
        assertEquals(
                BigDecimal.valueOf(120.44)
                        .multiply(BigDecimal.valueOf(0.5))
                        .multiply(BigDecimal.valueOf(6))
                        .multiply(BigDecimal.valueOf(0.8))
                        .setScale(2, BigDecimal.ROUND_HALF_UP),
                tester.getDisplayedPrice(new Discount(20)));
    }

    @Test
    public void testEmptyCartGetPrice() {
        Cart tester = new Cart();
        assertEquals(BigDecimal.valueOf(0).setScale(2, BigDecimal.ROUND_HALF_UP),
                tester.getDisplayedPrice(new Discount(0)));
    }
}
