package business;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ ChangePriceRequestTest.class, NewBookRequestTest.class, AddBooksRequestTest.class })
public class RequestTests {
	/*
     * Test suite for all Request classes
     */
}
