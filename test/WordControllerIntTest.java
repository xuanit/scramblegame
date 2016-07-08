import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static play.test.Helpers.*;
import static play.test.Helpers.HTMLUNIT;

/**
 * Created by xuan on 7/8/2016.
 */
public class WordControllerIntTest {
    /**
     * add your integration test here
     * in this example we just check if the welcome page is being shown
     */
    @Test
    public void test() {
        running(testServer(3333, fakeApplication(inMemoryDatabase())), HTMLUNIT, browser -> {
            browser.goTo("http://localhost:3333");
            assertTrue(browser.pageSource().contains("Your new application is ready."));
        });
    }
}
