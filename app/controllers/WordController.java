package controllers;

import play.mvc.*;
import services.WordService;

import javax.inject.Inject;

import java.util.Arrays;
import java.util.List;

import static play.libs.Json.toJson;

/**
 * Created by xuan on 7/7/2016.
 */
public class WordController extends Controller {

    private WordService wordService;

    @Inject
    public WordController(WordService wordService) {
        this.wordService = wordService;
    }

    public Result check(String checkingWord, List<String> checkedWords) {
        CheckRequest checkRequest = new CheckRequest();
        checkRequest.setCheckingWord(checkingWord);
        checkRequest.setCheckedWords(checkedWords);
        CheckResponse checkResponse = this.wordService.checkWord(checkRequest);
        if("abcde".equals(checkRequest.getCheckingWord())){
            checkResponse.setNextCharacters(Arrays.asList('a', 'b', 'c', 'd', 'e', 'f'));
        }
        return ok(toJson(checkResponse));
    }

    public Result getCharacters(){
        CharacterResponse response = new CharacterResponse();
        response.setCharacters(Arrays.asList('a', 'b', 'c', 'd', 'e'));
        return ok(toJson(response));
    }
}
