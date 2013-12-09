/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.data;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author alexander
 */
public class BookOrderTest {
    private static ValidatorFactory vf;
    private static Validator validator;
    
    @BeforeClass
    public static void init() {
        vf = Validation.buildDefaultValidatorFactory();
        validator = vf.getValidator();
    }
    
    @AfterClass
    public static void close() {
        vf.close();
    }
    
    @Test
    public void testValid() {
        final Isbn isbn = new Isbn("9783161484100");
        final Author author = new Author(new Credentials("Brian", "May"));
        final Book book = new Book.Builder(isbn, "foo", author).price(99.9).build();
        final List<BookOrderEntry> entries = new LinkedList<>();
        entries.add(new BookOrderEntry(book, 3));
        entries.add(new BookOrderEntry(book, 4));
        final BookOrder order = new BookOrder(entries);
        
        final Set<ConstraintViolation<BookOrder>> violations = validator.validate(order);
        assertEquals(0, violations.size());
        assertEquals(7 * book.getPrice(), order.getPrice(), 1e-10);
    }
    
    @Test
    public void testInvalid() {
        final Isbn isbn = new Isbn("9783161484100");
        final Author author = new Author(new Credentials("Brian", "May"));
        final Book book = new Book.Builder(isbn, "foo", author).price(99.9).build();
        final List<BookOrderEntry> entries = new LinkedList<>();
        entries.add(new BookOrderEntry(book, 3));
        entries.add(null);
        entries.add(null);
        final BookOrder order = new BookOrder(entries);
        
        final Set<ConstraintViolation<BookOrder>> violations = validator.validate(order);
        assertEquals(2, violations.size());
    }
}
