/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.ejb;

import edu.data.Author;
import edu.data.Book;
import edu.data.Credentials;
import edu.data.Isbn;
import edu.data.Publisher;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 *
 * @author alexander
 */
@RunWith(Arquillian.class)
public class BookEjbTest {
    private static Author author;
    
    @EJB
    private BookEjb bookEjb;
    @PersistenceContext
    EntityManager em;
    @Inject
    UserTransaction utx;
    
    
    @Deployment
    public static JavaArchive createTestArchive() {
        JavaArchive archive = ShrinkWrap.create(JavaArchive.class)
                .addPackage(BookEjb.class.getPackage())
                .addClass(Author.class)
                .addClass(Book.class)
                .addClass(Publisher.class)
                .addAsResource("test-persistence.xml", "META-INF/persistence.xml")
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
        return archive;
    }
    
    @Before
    public void initDatabase() throws Exception {
        utx.begin();
        em.joinTransaction();
        author = new Author(new Credentials("Brian", "May"));
        em.persist(author);
        utx.commit();
        em.clear();
    }
    
    @Test
    public void testCreateAndFindBook() throws Throwable {
        try {
            final Isbn isbn = new Isbn("9783161484100");
            Book book = new Book.Builder(isbn, "foo", author).build();

            book = bookEjb.createBook(book);
            Assert.assertNotNull(book.getId());
            book = bookEjb.findByIsbn(isbn);
            Assert.assertEquals("foo", book.getTitle());
        } catch(EJBException e) {
            throw e.getCause();
        }
    }
    
    @Test
    public void testFuzzySearch() throws Throwable {
        try {
            final Book book1 = new Book.Builder(new Isbn("1-84356-028-3"), 
                    "foobarbaz", author).build();
            final Book book2 = new Book.Builder(new Isbn("99921-58-10-7"), 
                    "bazfoo", author).build();
            final Book book3 = new Book.Builder(new Isbn("85-359-0277-5"), 
                    "barbar", author).build();

            bookEjb.createBook(book1);
            bookEjb.createBook(book2);
            bookEjb.createBook(book3);

            final List<Book> books = bookEjb.fuzzyFind("fo");
            Assert.assertEquals(2, books.size());
            Assert.assertTrue(books.get(0).getTitle().equals("bazfoo") ||
                              books.get(0).getTitle().equals("foobarbaz"));
        } catch(EJBException e) {
            throw e.getCause();
        }
    }
}