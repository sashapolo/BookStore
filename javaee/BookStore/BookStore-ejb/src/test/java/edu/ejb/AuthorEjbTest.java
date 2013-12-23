/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.ejb;

import edu.data.Author;
import edu.data.Credentials;
import java.util.List;
import javax.ejb.EJB;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 *
 * @author alexander
 */
@RunWith(Arquillian.class)
public class AuthorEjbTest {
    @EJB
    private AuthorEjb aejb;
    
    @Deployment
    public static JavaArchive createTestArchive() {
        JavaArchive archive = ShrinkWrap.create(JavaArchive.class)
                .addClasses(Author.class, AuthorEjb.class)
                .addAsResource("test-persistence.xml", "META-INF/persistence.xml")
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
        return archive;
    }
    
    @Test
    public void testFuzzyFind() {
        Author author = new Author(new Credentials("foo", "bar"));
        aejb.create(author);
        author = new Author(new Credentials("foo", "baz"));
        aejb.create(author);
        author = new Author(new Credentials("Pirate", "Jack"));
        aejb.create(author);
        
        List<Author> authors = aejb.findByCredentials("foo", "");
        Assert.assertEquals(2, authors.size());
        
        authors = aejb.findByCredentials("", "Jack");
        Assert.assertEquals(1, authors.size());
        
        authors = aejb.findByCredentials("foo", "baz");
        Assert.assertEquals(1, authors.size());
    }
}
