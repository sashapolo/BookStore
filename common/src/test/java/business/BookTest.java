package business;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.GregorianCalendar;

import static org.junit.Assert.assertEquals;


public final class BookTest {
    private static final double EPSILON = 1.0e-15;

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Test
    public void testBookGetPrice1() {
        final Book tester = new Book.Builder("",
                                       "",
                                       "",
                                       new Publisher(0, ""),
                                       new GregorianCalendar(),
                                       new Isbn("9783161484100"),
                                       120.44).build();
        assertEquals("Incorrect price", 120.44, tester.getPrice(), EPSILON);
    }

    @Test
    public void testBookGetPrice2() {
        final Book tester = new Book.Builder("",
                                       "",
                                       "",
                                       new Publisher(0, ""),
                                       new GregorianCalendar(),
                                       new Isbn("9783161484100"),
                                       120.44).discount(100).build();
        assertEquals("Incorrect price", 0, tester.getPrice(), EPSILON);
    }

    @Test
    public void testBookGetPrice3() {
        final Book tester = new Book.Builder("",
                                       "",
                                       "",
                                       new Publisher(0, ""),
                                       new GregorianCalendar(),
                                       new Isbn("9783161484100"),
                                       120.44).discount(48).build();
        assertEquals("Incorrect price", 120.44 * 0.52, tester.getPrice(), EPSILON);
    }

    @Test
    public void testBookIllegalArg() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("Illegal discount settings");
        @SuppressWarnings("unused")
        final Book tester = new Book.Builder("",
                                       "",
                                       "",
                                       new Publisher(0, ""),
                                       new GregorianCalendar(),
                                       new Isbn("9783161484100"),
                                       120.44).discount(-1).build();
    }
}
