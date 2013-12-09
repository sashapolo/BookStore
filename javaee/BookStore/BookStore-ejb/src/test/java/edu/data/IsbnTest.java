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
public class IsbnTest {
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
