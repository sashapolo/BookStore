package business;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;


@RunWith(Suite.class)
@SuiteClasses({ Isbn10Test.class, Isbn13Test.class })
public interface IsbnTests {
    /*
     * Test suite for all ISBN classes
     */
}
