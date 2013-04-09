package business;

import static org.junit.Assert.*;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;


public class Isbn13Test {

    @Rule
    private final ExpectedException exception = ExpectedException.none();

    @Test
    public void testIsbn13Normal() {
        final Isbn13 tester = new Isbn13("9783161484100");
        assertEquals("Incorrect ISBN construction", "9783161484100", tester.toString());
    }

    @Test
    public void testIsbn13WithDashes() {
        final Isbn13 tester = new Isbn13("978-3-16148-410-0");
        assertEquals("Incorrect ISBN construction", "9783161484100", tester.toString());
    }

    @Test
    public void testIsbn13NotValid() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("Invalid 13-digit ISBN number");
        @SuppressWarnings("unused")
        final Isbn13 tester = new Isbn13("9783161484103");
    }

    @Test
    public void testIsbn13WithLetters() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("Invalid 13-digit ISBN number");
        @SuppressWarnings("unused")
        final Isbn13 tester = new Isbn13("9783161A84103");
    }

    @Test
    public void testIsbn13IncorrectLength() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("Trying to create 13-digit ISBN from non 13-digit number");
        @SuppressWarnings("unused")
        final Isbn13 tester = new Isbn13("97831614841");
    }

    @Test
    public void testIsbn13FromIsbn10() {
        final Isbn13 tester = new Isbn13(new Isbn10("097522980X"));
        assertEquals("Incorrect ISBN10-to-ISBN13 conversion", "9780975229804", tester.toString());
    }
}
