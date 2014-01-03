/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.ejb;

import edu.data.Credentials;
import edu.data.User;
import javax.ejb.EJB;
import javax.ejb.EJBException;
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
public class UserEjbTest {
    @EJB
    private UserEjb ue;

    @Deployment
    public static JavaArchive createTestArchive() {
        JavaArchive archive = ShrinkWrap.create(JavaArchive.class)
                .addPackage(User.class.getPackage())
                .addClasses(User.class, UserEjb.class)
                .addAsResource("test-persistence.xml", "META-INF/persistence.xml")
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
        return archive;
    }
    
    @Test 
    public void testInsertValid() {
        User user = new User.Builder().login("Mad").password(1)
                .credentials(new Credentials("Mad", "Jack")).email("hello@world.com").build();
        user = ue.create(user);
        Assert.assertNotNull(user);
    }
    
    @Test(expected = EJBException.class)
    public void testInsertNonUnique() throws Throwable {
        final User user1 = new User.Builder().login("foo").password(1)
                .credentials(new Credentials("a", "b")).email("a@b").build();
        final User user2 = new User.Builder().login("foo").password(1)
                .credentials(new Credentials("a", "b")).email("abc@b").build();
        final User user3 = new User.Builder().login("fo").password(1)
                .credentials(new Credentials("a", "b")).email("a@b").build();
        
        ue.create(user1);
        ue.create(user2);
        ue.create(user3);
    }
    
}
