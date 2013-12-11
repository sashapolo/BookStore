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
public class IsbnTest {
    
    @Resource
    private Validator validator;
    
    @Deployment
    public static JavaArchive createTestArchive() {
        JavaArchive archive = ShrinkWrap.create(JavaArchive.class)
                .addClasses(Isbn.class)
                .addAsResource("test-persistence.xml", "META-INF/persistence.xml")
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
        return archive;
    }
    
    @Test
    public void testIsbn13Normal() {
        final Isbn tester = new Isbn("9783161484100");
        final Set<ConstraintViolation<Isbn>> violations = validator.validate(tester);
        assertEquals(0, violations.size());
        assertEquals("Incorrect ISBN construction", "9783161484100", tester.toString());
    }

    @Test
    public void testIsbn13WithDashes() {
        final Isbn tester = new Isbn("978-3-16148-410-0");
        final Set<ConstraintViolation<Isbn>> violations = validator.validate(tester);
        assertEquals(0, violations.size());
        assertEquals("Incorrect ISBN construction", "9783161484100", tester.toString());
    }

    @Test
    public void testIsbn13NotValid() {
        final Isbn tester = new Isbn("9783161484103");
        final Set<ConstraintViolation<Isbn>> violations = validator.validate(tester);
        assertEquals(1, violations.size());
    }

    @Test
    public void testIsbn13WithLetters() {
        final Isbn tester = new Isbn("9783161A84103");
        final Set<ConstraintViolation<Isbn>> violations = validator.validate(tester);
        assertEquals(1, violations.size());
    }

    @Test
    public void testIsbn13IncorrectLength() {
        final Isbn tester = new Isbn("97831614841");
        final Set<ConstraintViolation<Isbn>> violations = validator.validate(tester);
        assertEquals(1, violations.size());
    }

    @Test
    public void testIsbn13FromIsbn10() {
        final Isbn tester = new Isbn("097522980X");
        final Set<ConstraintViolation<Isbn>> violations = validator.validate(tester);
        assertEquals(0, violations.size());
        assertEquals("Incorrect ISBN10-to-ISBN13 conversion", "9780975229804", tester.toString());
    }
    
}
