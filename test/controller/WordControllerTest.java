package controller;

import com.fasterxml.jackson.databind.JsonNode;
import controllers.CheckRequest;
import controllers.CheckResponse;
import controllers.WordController;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import static  play.test.Helpers.*;
import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import play.libs.Json;
import play.mvc.Result;
import play.test.FakeRequest;
import play.test.Helpers;
import services.WordService;

/**
 * Created by xuan on 7/7/2016.
 */
@RunWith(MockitoJUnitRunner.class)
public class WordControllerTest {

    @Mock
    private WordService wordService;

    private WordController wordController;

    private CheckRequest checkRequest;

    private CheckResponse invalidResponse;

    private CheckResponse validResponse;

    @Before
    public void init() {

        this.wordController = new WordController(wordService);
        this.checkRequest = new CheckRequest();
        this.checkRequest.setCheckingWord("checking");
        this.invalidResponse = new CheckResponse();
        this.invalidResponse.setValid(false);
        this.validResponse = new CheckResponse();
        this.validResponse.setValid(true);
    }

    @Test
    public void testCheckValid(){
        when(this.wordService.checkWord(any(CheckRequest.class))).thenReturn(this.validResponse);
        Result result = Helpers.invokeWithContext(Helpers.fakeRequest().bodyJson(Json.toJson(this.checkRequest)),
                () -> this.wordController.check());
        Assert.assertEquals(200, result.status());
        JsonNode jsonNode = Json.parse(contentAsString(result));
        CheckResponse response = Json.fromJson(jsonNode, CheckResponse.class);
        Assert.assertEquals(true, response.isValid());
    }

    @Test
    public void testCheckInvalid() {
        when(this.wordService.checkWord(any(CheckRequest.class))).thenReturn(this.invalidResponse);
        Result result = Helpers.invokeWithContext(Helpers.fakeRequest().bodyJson(Json.toJson(checkRequest)),
                () -> this.wordController.check());
        Assert.assertEquals(200, result.status());
        JsonNode jsonNode = Json.parse(contentAsString(result));
        CheckResponse response = Json.fromJson(jsonNode, CheckResponse.class);
        Assert.assertEquals(false, response.isValid());
    }


    @Test
    public void testCheckEmptyRequest(){
        Result result = Helpers.invokeWithContext(Helpers.fakeRequest().bodyText(""),
                () -> this.wordController.check());
        assertEquals(400, result.status());
    }
}
