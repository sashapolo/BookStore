package edu.business;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.assertEquals;


public final class IsbnTest {

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Test
    public void testIsbn13Normal() {
        final Isbn tester = new Isbn("9783161484100");
        assertEquals("Incorrect ISBN construction", "9783161484100", tester.toString());
    }

    @Test
    public void testIsbn13WithDashes() {
        final Isbn tester = new Isbn("978-3-16148-410-0");
        assertEquals("Incorrect ISBN construction", "9783161484100", tester.toString());
    }

    @Test
    public void testIsbn13NotValid() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("Invalid 13-digit ISBN number");
        @SuppressWarnings("unused")
        final Isbn tester = new Isbn("9783161484103");
    }

    @Test
    public void testIsbn13WithLetters() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("Invalid 13-digit ISBN number");
        @SuppressWarnings("unused")
        final Isbn tester = new Isbn("9783161A84103");
    }

    @Test
    public void testIsbn13IncorrectLength() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("Trying to create 13-digit ISBN from non 13-digit number");
        @SuppressWarnings("unused")
        final Isbn tester = new Isbn("97831614841");
    }

    @Test
    public void testIsbn13FromIsbn10() {
        final Isbn tester = new Isbn("097522980X");
        assertEquals("Incorrect ISBN10-to-ISBN13 conversion", "9780975229804", tester.toString());
    }
}
