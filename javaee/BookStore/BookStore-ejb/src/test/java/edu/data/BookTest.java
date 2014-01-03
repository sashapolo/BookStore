/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.data;

import java.util.GregorianCalendar;
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
public class BookTest {
    
    @Resource
    private Validator validator;
    
    @Deployment
    public static JavaArchive createTestArchive() {
        JavaArchive archive = ShrinkWrap.create(JavaArchive.class)
                .addClasses(Author.class, Book.class, Publisher.class)
                .addAsResource("test-persistence.xml", "META-INF/persistence.xml")
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
        return archive;
    }
    
    @Test
    public void testValid() {
        final Isbn isbn = new Isbn("9783161484100");
        final Author author = new Author(new Credentials("Brian", "May"));
        final Book book = new Book.Builder().isbn(isbn).title("foo").author(author).build();
        
        final Set<ConstraintViolation<Book>> violations = validator.validate(book);
        assertEquals(0, violations.size());
    }
    
    @Test
    public void testEmptyTitle() {
        final Isbn isbn = new Isbn("9783161484100");
        final Author author = new Author(new Credentials("Brian", "May"));
        final Book book = new Book.Builder().isbn(isbn).title("").author(author).build();
        
        final Set<ConstraintViolation<Book>> violations = validator.validate(book);
        assertEquals(1, violations.size());
    }
    
    @Test
    public void testNullParameters() {
        final Book book = new Book.Builder().build();
        
        final Set<ConstraintViolation<Book>> violations = validator.validate(book);
        assertEquals(3, violations.size());
    }
    
    @Test
    public void testInvalidParameters() {
        final Isbn isbn = new Isbn("9583161484100");
        final Author author = new Author(new Credentials("Brian", "May"));
        final Book book = new Book.Builder().isbn(isbn).title("abc").author(author)
                .discount(new Discount(101)).price(-10).build();
        
        final Set<ConstraintViolation<Book>> violations = validator.validate(book);
        assertEquals(3, violations.size());
    }
    
    @Test
    public void testPriceWithDiscount() {
        final Isbn isbn = new Isbn("9583161484100");
        final Author author = new Author(new Credentials("Brian", "May"));
        
        Book book = new Book.Builder().isbn(isbn).title("abc").author(author)
                .discount(new Discount(95)).price(65).build();
        assertEquals(65.0 * 5 / 100, book.getPrice(), 1e-15);
        
        book = new Book.Builder().isbn(isbn).title("abc").author(author)
                .discount(new Discount(0)).price(65).build();
        assertEquals(65, book.getPrice(), 1e-15);
        
        book = new Book.Builder().isbn(isbn).title("abc").author(author)
                .discount(new Discount(100)).price(65).build();
        assertEquals(0, book.getPrice(), 1e-15);
    }
}
