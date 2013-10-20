package business;

import static org.junit.Assert.assertEquals;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;


public final class IsbnTest {

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Test
    public void testIsbn13Normal() throws WrongFormatException {
        final Isbn tester = new Isbn("9783161484100");
        assertEquals("Incorrect ISBN construction", "9783161484100", tester.toString());
    }

    @Test
    public void testIsbn13WithDashes() throws WrongFormatException {
        final Isbn tester = new Isbn("978-3-16148-410-0");
        assertEquals("Incorrect ISBN construction", "9783161484100", tester.toString());
    }

    @Test
    public void testIsbn13NotValid() throws WrongFormatException {
        exception.expect(WrongFormatException.class);
        exception.expectMessage("Invalid 13-digit ISBN number");
        @SuppressWarnings("unused")
        final Isbn tester = new Isbn("9783161484103");
    }

    @Test
    public void testIsbn13WithLetters() throws WrongFormatException {
        exception.expect(WrongFormatException.class);
        exception.expectMessage("Invalid 13-digit ISBN number");
        @SuppressWarnings("unused")
        final Isbn tester = new Isbn("9783161A84103");
    }

    @Test
    public void testIsbn13IncorrectLength() throws WrongFormatException {
        exception.expect(WrongFormatException.class);
        exception.expectMessage("Trying to create 13-digit ISBN from non 13-digit number");
        @SuppressWarnings("unused")
        final Isbn tester = new Isbn("97831614841");
    }

    @Test
    public void testIsbn13FromIsbn10() throws WrongFormatException {
        final Isbn tester = new Isbn("097522980X");
        assertEquals("Incorrect ISBN10-to-ISBN13 conversion", "9780975229804", tester.toString());
    }
}
