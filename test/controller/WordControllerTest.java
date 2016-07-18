package controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.collect.Lists;
import controllers.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import static  play.test.Helpers.*;
import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

import org.junit.runner.RunWith;
import org.mockito.ArgumentMatcher;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import play.libs.Json;
import play.mvc.Http;
import play.mvc.Result;
import services.CheckRequest;
import services.CheckResponse;
import services.WordService;

import java.util.*;

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

    @Mock
    Http.Request request;

    @Mock
    Http.RequestHeader header;

    private Map<String, String> flashData;

    @Before
    public void init() {

        this.wordController = new WordController(wordService);
        this.checkRequest = new CheckRequest();
        this.checkRequest.setCheckingWord("checking");
        this.invalidResponse = new CheckResponse();
        this.invalidResponse.setValid(false);
        this.validResponse = new CheckResponse();
        this.validResponse.setValid(true);


        flashData = new HashMap<>();
        flashData.put(WordController.PROVIDED_WORD_SESSION, "test");
        flashData.put(WordController.NUM_WORDS_SESSION, "1");
        Map<String, Object> argData = Collections.emptyMap();
        Long id = 2L;
        play.api.mvc.RequestHeader header = mock(play.api.mvc.RequestHeader.class);
        Http.Context context = new Http.Context(id, header, request, flashData, flashData, argData);
        Http.Context.current.set(context);
    }

    @Test
    public void testCheckValidWithoutNextCharacter(){
        CheckResponse checkResponse = new CheckResponse();
        checkResponse.setValid(true);
        when(this.wordService.checkWord(any(CheckRequest.class))).thenReturn(checkResponse);
        Result result = this.wordController.check(this.checkRequest.getCheckingWord(), Arrays.asList("checkedWords"));
        Assert.assertEquals(200, result.status());
        JsonNode jsonNode = Json.parse(contentAsString(result));
        CheckResponse response = Json.fromJson(jsonNode, CheckResponse.class);
        assertEquals(true, response.isValid());
        verify(this.wordService).checkWord(any(CheckRequest.class));
        assertEquals(null, response.getNextCharacters());
    }

    @Test
    public void testCheckValidWithNextCharacter(){
        CheckResponse checkResponse = new CheckResponse();
        checkResponse.setValid(true);
        checkResponse.setNextCharacters(Lists.charactersOf("nextChars"));
        List<String> words = Arrays.asList("a", "b");
        when(this.wordService.checkWord(any(CheckRequest.class))).thenReturn(checkResponse);
        when(this.wordService.getAllWords(anyString())).thenReturn(words);
        String originalNumOfWordsString = wordController.session(WordController.NUM_WORDS_SESSION);
        Result result = this.wordController.check(this.checkRequest.getCheckingWord(), Arrays.asList("checkedWords"));
        Assert.assertEquals(200, result.status());
        JsonNode jsonNode = Json.parse(contentAsString(result));
        CheckResponse response = Json.fromJson(jsonNode, CheckResponse.class);
        assertEquals(true, response.isValid());
        verify(this.wordService).checkWord(argThat(new ArgumentMatcher<CheckRequest>() {
            @Override
            public boolean matches(Object o) {

                return String.valueOf(((CheckRequest)o).getNumOfWords()).equals(originalNumOfWordsString);
            }
        }));
        verify(this.wordService).getAllWords(anyString());
        assertEquals(checkResponse.getNextCharacters(), response.getNextCharacters());
        assertEquals("nextChars", this.wordController.session(WordController.PROVIDED_WORD_SESSION));
        assertEquals(String.valueOf(words.size()), this.wordController.session(WordController.NUM_WORDS_SESSION));
    }

    @Test
    public void testCheckInvalid() {
        flashData.put(WordController.PROVIDED_WORD_SESSION, "test");
        when(this.wordService.checkWord(any(CheckRequest.class))).thenReturn(this.invalidResponse);
        Result result = this.wordController.check(this.checkRequest.getCheckingWord(), Arrays.asList("checkedWords"));
        Assert.assertEquals(200, result.status());
        JsonNode jsonNode = Json.parse(contentAsString(result));
        CheckResponse response = Json.fromJson(jsonNode, CheckResponse.class);
        assertEquals(false, response.isValid());
        verify(this.wordService).checkWord(any(CheckRequest.class));
    }

    @Test
    public void testGetWord(){
        String word = "returningWord";
        when(this.wordService.getCharacters()).thenReturn(Lists.charactersOf(word));
        List<String> words = Arrays.asList("a", "b");
        when(this.wordService.getAllWords(any())).thenReturn(words);
        Result result = this.wordController.getCharacters();
        assertEquals(200, result.status());
        JsonNode jsonNode = Json.parse(contentAsString(result));
        CharactersResponse response = Json.fromJson(jsonNode, CharactersResponse.class);
        assertEquals(Lists.charactersOf(word), response.getCharacters());
        assertEquals(word, this.wordController.session(WordController.PROVIDED_WORD_SESSION));
        assertEquals(String.valueOf(words.size()), this.wordController.session(WordController.NUM_WORDS_SESSION));
    }

    @Test
    public void testGetAllWords(){
        String providedWord = this.wordController.session(WordController.PROVIDED_WORD_SESSION);
        List<String> allWords = Arrays.asList("dr", "wo");
        when(this.wordService.getAllWords(providedWord)).thenReturn(allWords);
        Result result = this.wordController.getAllWords();
        assertEquals(200, result.status());
        JsonNode jsonNode = Json.parse(contentAsString(result));
        AllWordsResponse response = Json.fromJson(jsonNode, AllWordsResponse.class);
        verify(this.wordService).getAllWords(providedWord);
        assertEquals(allWords, response.getWords());

    }
}
