package controllers;

import play.db.jpa.Transactional;
import play.mvc.*;
import services.WordService;

import javax.inject.Inject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static play.libs.Json.toJson;

/**
 * Created by xuan on 7/7/2016.
 */
public class WordController extends Controller {

    public static final String PROVIDED_WORD_SESSION =  "provided_word";
    private WordService wordService;

    @Inject
    public WordController(WordService wordService) {
        this.wordService = wordService;
    }

    @Transactional(readOnly = true)
    public Result check(String checkingWord, List<String> checkedWords) {
        CheckRequest checkRequest = new CheckRequest();
        checkRequest.setCheckingWord(checkingWord);
        String providedWord = session(PROVIDED_WORD_SESSION);
        CheckResponse checkResponse = new CheckResponse();
        if(providedWord != null) {
            checkRequest.setScrambledWord(providedWord);
            checkRequest.setCheckedWords(new ArrayList<>(checkedWords));
            checkResponse = this.wordService.checkWord(checkRequest);
            if(checkResponse.getNextCharacters() != null) {
                session(PROVIDED_WORD_SESSION, convertToString(checkResponse.getNextCharacters()));
            }
        }
        return ok(toJson(checkResponse));
    }

    private String convertToString(List<Character> characters) {
        StringBuilder builder = new StringBuilder(characters.size());
        for(Character ch: characters)
        {
            builder.append(ch);
        }
        return builder.toString();
    }
    @Transactional(readOnly = true)
    public Result getCharacters(){
        CharacterResponse response = new CharacterResponse();
        response.setCharacters(this.wordService.getCharacters());
        session(PROVIDED_WORD_SESSION,convertToString(response.getCharacters()));
        return ok(toJson(response));
    }

    @Transactional(readOnly = true)
    public Result getAllWords() {
        AllWordsResponse response = new AllWordsResponse();
        response.setWords(this.wordService.getAllWords(session(PROVIDED_WORD_SESSION)));
        return ok(toJson(response));
    }
}
