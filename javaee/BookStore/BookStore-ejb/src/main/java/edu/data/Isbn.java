/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.data;

import java.io.Serializable;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

/**
 *
 * @author alexander
 */
@Embeddable
public class Isbn implements Serializable {
    private static final long serialVersionUID = 1L;
    @NotNull
    @IsbnAnno
    private final String isbn;
    
    protected Isbn() {
        isbn = "";
    }
    
    public Isbn(final String isbn) {
        String tmp = isbn.replace("-", "");
        if (tmp.length() == 10) {
            tmp = toIsbn13(tmp);
        }
        this.isbn = tmp;
    }
    
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
