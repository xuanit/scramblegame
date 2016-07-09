package controller;

import com.fasterxml.jackson.databind.JsonNode;
import controllers.CharacterResponse;
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
import services.WordService;

import java.util.Arrays;

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
        Result result = this.wordController.check(this.checkRequest.getCheckingWord(), Arrays.asList("checkedWords"));
        Assert.assertEquals(200, result.status());
        JsonNode jsonNode = Json.parse(contentAsString(result));
        CheckResponse response = Json.fromJson(jsonNode, CheckResponse.class);
        assertEquals(true, response.isValid());
    }

    @Test
    public void testCheckInvalid() {
        when(this.wordService.checkWord(any(CheckRequest.class))).thenReturn(this.invalidResponse);
        Result result = this.wordController.check(this.checkRequest.getCheckingWord(), Arrays.asList("checkedWords"));
        Assert.assertEquals(200, result.status());
        JsonNode jsonNode = Json.parse(contentAsString(result));
        CheckResponse response = Json.fromJson(jsonNode, CheckResponse.class);
        assertEquals(false, response.isValid());
    }

    @Test
    public void testGetWord(){
        Result result = this.wordController.getCharacters();
        assertEquals(200, result.status());
        JsonNode jsonNode = Json.parse(contentAsString(result));
        CharacterResponse response = Json.fromJson(jsonNode, CharacterResponse.class);
        assertNotNull(response.getCharacters());

    }
}
