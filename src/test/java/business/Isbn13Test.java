package business;

import static org.junit.Assert.*;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class Isbn13Test {

	@Rule
	public ExpectedException exception = ExpectedException.none();
	
	@Test
	public void testIsbn13Normal() {
		Isbn13 tester = new Isbn13("9783161484100");
		assertEquals("9783161484100", tester.toString());
	}

	@Test
	public void testIsbn13NotValid() {
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage("Invalid 13-digit ISBN number");
		@SuppressWarnings("unused")
		Isbn13 tester = new Isbn13("9783161484103");
	}
	
	@Test
	public void testIsbn13WithLetters() {
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage("Invalid 13-digit ISBN number");
		@SuppressWarnings("unused")
		Isbn13 tester = new Isbn13("9783161A84103");
	}
	
	@Test
	public void testIsbn13IncorrectLength() {
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage("Trying to create 13-digit ISBN from non 13-digit number");
		@SuppressWarnings("unused")
		Isbn13 tester = new Isbn13("97831614841");
	}
	
	@Test
	public void testIsbn13FromIsbn10() {
		Isbn13 tester = new Isbn13(new Isbn10("097522980X"));
		assertEquals("9780975229804", tester.toString());
	}
}
