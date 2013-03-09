package business;

import static org.junit.Assert.*;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class Isbn10Test {

	@Rule
	public ExpectedException exception = ExpectedException.none();

	
	@Test
	public void testIsbn10Normal() {
		Isbn10 tester = new Isbn10("9992158107");
		assertEquals("9992158107", tester.getIsbn());
	}
	
	@Test
	public void testIsbn10NormalWithX() {
		Isbn10 tester = new Isbn10("097522980X");
		assertEquals("097522980X", tester.getIsbn());
	}

	@Test
	public void testIsbn10NotValid() {
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage("Invalid 10-digit ISBN number");
		Isbn10 tester = new Isbn10("0975229801");
	}
	
	@Test
	public void testIsbn10WithLetters() {
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage("Invalid 10-digit ISBN number");
		Isbn10 tester = new Isbn10("09752F9801");
	}
	
	@Test
	public void testIsbn10IncorrectLength() {
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage("Trying to create 10-digit ISBN from non 10-digit number");
		Isbn10 tester = new Isbn10("97831614841");
	}

}
