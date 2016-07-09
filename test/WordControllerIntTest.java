import org.junit.Test;
import play.Environment;
import play.Mode;
import play.libs.ws.WSClient;
import play.libs.ws.WSResponse;
import play.test.TestServer;

import java.io.File;
import java.util.concurrent.CompletionStage;

import static org.junit.Assert.*;
import static play.test.Helpers.*;
import static play.test.Helpers.HTMLUNIT;

import play.Application;
import play.inject.guice.GuiceApplicationBuilder;

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
    }
}
