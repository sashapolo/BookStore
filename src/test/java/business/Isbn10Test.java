package business;

import static org.junit.Assert.*;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;


public class Isbn10Test {

    @Rule
    private final ExpectedException exception = ExpectedException.none();

    @Test
    public void testIsbn10Normal() {
        final Isbn10 tester = new Isbn10("9992158107");
        assertEquals("Incorrect ISBN construction", "9992158107", tester.toString());
    }

    @Test
    public void testIsbn10NormalWithX() {
        final Isbn10 tester = new Isbn10("097522980X");
        assertEquals("Incorrect ISBN construction", "097522980X", tester.toString());
    }
    
    @Test
    public void testIsbn10WithDashes() {
        final Isbn10 tester = new Isbn10("99921-58-10-7");
        assertEquals("Incorrect ISBN construction", "9992158107", tester.toString());
    }

    @Test
    public void testIsbn10NotValid() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("Invalid 10-digit ISBN number");
        @SuppressWarnings("unused")
        final Isbn10 tester = new Isbn10("0975229801");
    }

    @Test
    public void testIsbn10WithLetters() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("Invalid 10-digit ISBN number");
        @SuppressWarnings("unused")
        final Isbn10 tester = new Isbn10("09752F9801");
    }

    @Test
    public void testIsbn10IncorrectLength() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("Trying to create 10-digit ISBN from non 10-digit number");
        @SuppressWarnings("unused")
        final Isbn10 tester = new Isbn10("97831614841");
    }

}
