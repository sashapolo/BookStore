/**
 * 
 */
package business;

/**
 * @author alexander
 *
 */
public class Isbn13 extends Isbn {
	public Isbn13(final String isbn) throws IllegalArgumentException {
		super(isbn);
		if (isbn.length() != 13) {
			throw new IllegalArgumentException("Trying to create 13-digit ISBN from non 13-digit number");
		}
		if (!isValid(isbn)) {
			throw new IllegalArgumentException("Invalid 13-digit ISBN number");
		}
	}
	
	@Override
	protected boolean isValid(final String isbn) {
		int check = 0;
		char digits[] = isbn.toCharArray();
	    for (int i = 0; i < 13; i += 2) {
	        check += (digits[i] - '0');
	    }
	    for (int i = 1; i < 12; i += 2) {
	        check += (digits[i] - '0') * 3;
	    }
	    return check % 10 == 0;
	}
}
