/**
 * 
 */
package business;

/**
 * @author alexander
 * 
 */
public class Isbn13 extends Isbn {
    public Isbn13(final String isbn) {
        assert (isbn != null);

        final String tmp = isbn.replace("-", "");
        if (tmp.length() != 13) {
            throw new IllegalArgumentException(
                    "Trying to create 13-digit ISBN from non 13-digit number");
        }
        if (!isValid(tmp)) {
            throw new IllegalArgumentException("Invalid 13-digit ISBN number");
        }
        isbn_ = tmp;
    }

    public Isbn13(final Isbn10 isbn10) {
        assert (isbn10 != null);

        final StringBuilder tmp = new StringBuilder("978");
        tmp.append(isbn10.toString().substring(0, 9));
        tmp.append(getLastDigit(tmp.toString()));
        isbn_ = tmp.toString();

        assert isValid(isbn_);
    }

    @Override
    public Isbn13 toIsbn13() {
        return this;
    }

	private char getLastDigit(final String isbn) {
        int result = 0;
        final char digits[] = isbn.toCharArray();
        final int isbnLength = isbn.length();
        for (int i = 1; i < isbnLength; i += 2) {
            result += digits[i] - '0';
        }
        result *= 3;
        for (int i = 0; i < isbnLength; i += 2) {
            result += digits[i] - '0';
        }
        result %= 10;
        return result == 0 ? '0' : (char) ((10 - result) + '0');
    }

    @Override
    protected boolean isValid(final String isbn) {
        int check = 0;
        final char digits[] = isbn.toCharArray();
        final int isbnLength = isbn.length();
        for (int i = 1; i < isbnLength; i += 2) {
            check += (digits[i] - '0');
        }
        check *= 3;
        for (int i = 0; i < isbnLength; i += 2) {
            check += (digits[i] - '0');
        }
        return check % 10 == 0;
    }
}
