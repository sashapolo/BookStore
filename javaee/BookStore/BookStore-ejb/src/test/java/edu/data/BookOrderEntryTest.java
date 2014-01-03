/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.data;

import java.util.Set;
import javax.annotation.Resource;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 *
 * @author alexander
 */
@RunWith(Arquillian.class)
public class BookOrderEntryTest {
    
    @Resource
    private Validator validator;
    
    @Deployment
    public static JavaArchive createTestArchive() {
        JavaArchive archive = ShrinkWrap.create(JavaArchive.class)
                .addClasses(Author.class, Book.class, Publisher.class, BookOrderEntry.class)
                .addAsResource("test-persistence.xml", "META-INF/persistence.xml")
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
        return archive;
    }
    
    @Test
    public void testValid() {
        final Isbn isbn = new Isbn("9783161484100");
        final Author author = new Author(new Credentials("Brian", "May"));
        final Book book = new Book.Builder().isbn(isbn).title("foo").author(author).build();
        final BookOrderEntry tester = new BookOrderEntry(book, 1);
        
        final Set<ConstraintViolation<BookOrderEntry>> violations = validator.validate(tester);
        assertEquals(0, violations.size());
    }
    
    @Test
    public void testInvalidBook() {
        final Isbn isbn = new Isbn("9783141484100");
        final Author author = new Author(new Credentials("Brian", "May"));
        final Book book = new Book.Builder().isbn(isbn).author(author).build();
        final BookOrderEntry tester = new BookOrderEntry(book, 1);
        
        final Set<ConstraintViolation<BookOrderEntry>> violations = validator.validate(tester);
        assertEquals(2, violations.size());
    }
    
    @Test
    public void testInvalidAmount() {
        final Isbn isbn = new Isbn("9783161484100");
        final Author author = new Author(new Credentials("Brian", "May"));
        final Book book = new Book.Builder().isbn(isbn).title("foo").author(author).build();
        final BookOrderEntry tester = new BookOrderEntry(book, -3);
        
        final Set<ConstraintViolation<BookOrderEntry>> violations = validator.validate(tester);
        assertEquals(1, violations.size());
    }
    
    @Test
    public void testPrice() {
        final Isbn isbn = new Isbn("9783161484100");
        final Author author = new Author(new Credentials("Brian", "May"));
        final Book book = new Book.Builder().isbn(isbn).title("foo").author(author).price(99.9).build();
        final BookOrderEntry tester = new BookOrderEntry(book, 3);
        assertEquals(99.9 * 3, tester.getPrice(), 1e-15);
    }
}
