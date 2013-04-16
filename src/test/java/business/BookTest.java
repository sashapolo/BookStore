package business;

import static org.junit.Assert.*;

import java.util.GregorianCalendar;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;


public class BookTest {
    public static final double EPSILON = 1e-15;

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Test
    public void testBookGetPrice1() {
        final Book tester = new Book(0,
                                     "",
                                     "",
                                     new Publisher(0, "", "", "", ""),
                                     new GregorianCalendar(),
                                     new Isbn13("9783161484100"),
                                     120.44,
                                     0);
        assertEquals("Incorrect price", 120.44, tester.getPrice(), EPSILON);
    }

    @Test
    public void testBookGetPrice2() {
        final Book tester = new Book(0,
                                     "",
                                     "",
                                     new Publisher(0, "", "", "", ""),
                                     new GregorianCalendar(),
                                     new Isbn13("9783161484100"),
                                     120.44,
                                     100);
        assertEquals("Incorrect price", 0, tester.getPrice(), EPSILON);
    }

    @Test
    public void testBookGetPrice3() {
        final Book tester = new Book(0,
                                     "",
                                     "",
                                     new Publisher(0, "", "", "", ""),
                                     new GregorianCalendar(),
                                     new Isbn13("9783161484100"),
                                     120.44,
                                     48);
        assertEquals("Incorrect price", 120.44 * 0.52, tester.getPrice(), EPSILON);
    }

    @Test
    public void testBookIllegalArg() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("Illegal discount settings");
        @SuppressWarnings("unused")
        final Book tester = new Book(0,
                                     "",
                                     "",
                                     new Publisher(0, "", "", "", ""),
                                     new GregorianCalendar(),
                                     new Isbn13("9783161484100"),
                                     120.44,
                                     -1);
    }
}
