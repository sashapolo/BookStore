package edu.business;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;


@RunWith(Suite.class)
@SuiteClasses({ CartTest.class, BookTest.class, IsbnTests.class, OrderEntryTest.class })
public interface LogicTests {
    /*
     * Test suite for all business logic classes
     */
}
