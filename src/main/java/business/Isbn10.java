package business;

/**
 * @author alexander
 * 
 */
public final class Isbn10 extends Isbn {
    public Isbn10(final String isbn) throws WrongFormatException {
        assert (isbn != null);

        final String tmp = isbn.replace("-", "");
        if (tmp.length() != 10) {
            throw new WrongFormatException("Trying to create 10-digit ISBN from non 10-digit number");
        }
        if (!isValid(tmp)) {
            throw new WrongFormatException("Invalid 10-digit ISBN number");
        }
        isbn_ = tmp;
    }

    @Override
    public Isbn toIsbn13() {
        return new Isbn13(this);
    }

    @Override
    protected boolean isValid(final String isbn) {
        int check = 0;
        final char digits[] = isbn.toCharArray();
        for (int i = isbn.length(); i > 1; i--) {
            check += i * (digits[10 - i] - '0');
        }
        check += (digits[9] == 'X') ? 10 : (digits[9] - '0');
        return check % 11 == 0;
    }
}
