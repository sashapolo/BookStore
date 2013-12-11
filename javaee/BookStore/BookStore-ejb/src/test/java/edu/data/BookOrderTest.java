/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.data;

import java.util.LinkedList;
import java.util.List;
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
public class BookOrderTest {
    @Resource
    private Validator validator;
    
    @Deployment
    public static JavaArchive createTestArchive() {
        JavaArchive archive = ShrinkWrap.create(JavaArchive.class)
                .addClasses(BookOrder.class)
                .addAsResource("test-persistence.xml", "META-INF/persistence.xml")
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
        return archive;
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
        final Isbn isbn = new Isbn("9583161484100");
        final Author author = new Author(new Credentials("Brian", "May"));
        final Book book = new Book.Builder(isbn, null, author).price(99.9).build();
        final List<BookOrderEntry> entries = new LinkedList<>();
        entries.add(new BookOrderEntry(book, 3));
        final BookOrder order = new BookOrder(entries);
        
        final Set<ConstraintViolation<BookOrder>> violations = validator.validate(order);
        assertEquals(2, violations.size());
    }
    
    @Test(expected = NullPointerException.class)
    public void testNullEntry() {
        final List<BookOrderEntry> entries = new LinkedList<>();
        entries.add(null);
        entries.add(null);
        final BookOrder order = new BookOrder(entries);
    }
}
