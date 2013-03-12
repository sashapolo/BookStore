package business;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.util.GregorianCalendar;

import org.junit.Before;
import org.junit.Test;


public class BasketTest {
    private Book book;

    @Before
    public void init() {
        book = new Book("", 
                        "", 
                        new Publisher(0, "", ""), 
                        new GregorianCalendar(), 
                        new Isbn13("9783161484100"), 
                        new BigDecimal(120.44), 
                        50);
    }

    @Test
    public void testBasketGetPrice1() {
        Basket tester = new Basket();
        tester.add(new OrderEntry(book, 2));
        tester.add(new OrderEntry(book, 2));
        tester.add(new OrderEntry(book, 2));
        assertEquals(3, tester.size());
        assertEquals(
                BigDecimal.valueOf(120.44)
                        .multiply(BigDecimal.valueOf(0.5))
                        .multiply(BigDecimal.valueOf(6))
                        .multiply(BigDecimal.valueOf(0.8))
                        .setScale(2, BigDecimal.ROUND_HALF_UP),
                tester.getDisplayedPrice(new Discount(20)));
    }

    @Test
    public void testEmptyBasketGetPrice() {
        Basket tester = new Basket();
        assertEquals(BigDecimal.valueOf(0).setScale(2, BigDecimal.ROUND_HALF_UP),
                tester.getDisplayedPrice(new Discount(0)));
    }
}
