/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.data;

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
public class BookOrderEntryTest {
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
        final Book book = new Book.Builder(isbn, "foo", author).build();
        final BookOrderEntry tester = new BookOrderEntry(book, 1);
        
        final Set<ConstraintViolation<BookOrderEntry>> violations = validator.validate(tester);
        assertEquals(0, violations.size());
    }
    
    @Test
    public void testInvalidBook() {
        final Isbn isbn = new Isbn("9783141484100");
        final Author author = new Author(new Credentials("Brian", "May"));
        final Book book = new Book.Builder(isbn, null, author).build();
        final BookOrderEntry tester = new BookOrderEntry(book, 1);
        
        final Set<ConstraintViolation<BookOrderEntry>> violations = validator.validate(tester);
        assertEquals(2, violations.size());
    }
    
    @Test
    public void testInvalidAmount() {
        final Isbn isbn = new Isbn("9783161484100");
        final Author author = new Author(new Credentials("Brian", "May"));
        final Book book = new Book.Builder(isbn, "foo", author).build();
        final BookOrderEntry tester = new BookOrderEntry(book, -3);
        
        final Set<ConstraintViolation<BookOrderEntry>> violations = validator.validate(tester);
        assertEquals(1, violations.size());
    }
    
    @Test
    public void testPrice() {
        final Isbn isbn = new Isbn("9783161484100");
        final Author author = new Author(new Credentials("Brian", "May"));
        final Book book = new Book.Builder(isbn, "foo", author).price(99.9).build();
        final BookOrderEntry tester = new BookOrderEntry(book, 3);
        assertEquals(99.9 * 3, tester.getPrice(), 1e-15);
    }
}
