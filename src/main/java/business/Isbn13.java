/**
 * 
 */
package business;

/**
 * @author alexander
 *
 */
public class Isbn13 {
	// FIXME I don't know how this class is going to be used, so this form
	// of internal representation is probably fucked up
	private String isbn_;
	
	public Isbn13(final String isbn) throws IllegalArgumentException {
		if (isbn.length() != 13) {
			throw new IllegalArgumentException("Trying to create 13-digit ISBN from non 13-digit number");
		}
		if (!isValid(isbn)) {
			throw new IllegalArgumentException("Invalid 13-digit ISBN number");
		}
		isbn_ = isbn;
	}
	
	private boolean isValid(final String isbn) {
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
	
	public String getIsbn() {
		return isbn_;
	}

	@Override
	public String toString() {
		return isbn_;
	}
}
