/**
 * 
 */
package edu.business;

/**
 * @author alexander
 * 
 */
public final class Isbn {
    private final String isbn;

    private static String toIsbn13(final String isbn) {
        final StringBuilder tmp = new StringBuilder("978");
        tmp.append(isbn.substring(0, 9));
        tmp.append(getLastDigit(tmp.toString()));
        return tmp.toString();
    }

    private static char getLastDigit(final String isbn) {
        int result = 0;
        final char[] digits = isbn.toCharArray();
        final int isbnLength = isbn.length();
        for (int i = 1; i < isbnLength; i += 2) {
            result += digits[i] - '0';
        }
        result *= 3;
        for (int i = 0; i < isbnLength; i += 2) {
            result += digits[i] - '0';
        }
        result %= 10;
        return result == 0 ? '0' : (char) (10 - result + '0');
    }

    public Isbn(final String isbn) {
        assert isbn != null;

        String tmp = isbn.replace("-", "");
        if (tmp.length() == 10) {
            tmp = toIsbn13(tmp);
        }
        if (!isValid(tmp)) {
            throw new IllegalArgumentException("Invalid isbn");
        }
        this.isbn = tmp;
    }

    public static boolean isValid(final String isbn) {
        final int length = isbn.length();
        if (length != 13 && length != 10) return false;

        int check = 0;
        final char[] digits = isbn.toCharArray();
        for (int i = 1; i < length; i += 2) {
            check += digits[i] - '0';
        }
        check *= 3;
        for (int i = 0; i < length; i += 2) {
            check += digits[i] - '0';
        }
        return check % 10 == 0;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        final Isbn isbn1 = (Isbn) obj;

        return isbn.equals(isbn1.isbn);
    }

    @Override
    public int hashCode() {
        return isbn.hashCode();
    }

    @Override
    public String toString() {
        return isbn;
    }
}
