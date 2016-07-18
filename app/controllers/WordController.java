package controllers;

import play.db.jpa.Transactional;
import play.mvc.*;
import services.CheckRequest;
import services.CheckResponse;
import services.IWordService;
import services.WordService;

import javax.inject.Inject;

import java.util.ArrayList;
import java.util.List;

import static play.libs.Json.toJson;

/**
 * Created by xuan on 7/7/2016.
 */
public class WordController extends Controller {

    public static final String PROVIDED_WORD_SESSION =  "provided_word";

    public static final String NUM_WORDS_SESSION =  "num_of_words";

    private IWordService wordService;

    @Inject
    public WordController(IWordService wordService) {
        this.wordService = wordService;
    }

    /**
     * Checks whether the checking word is valid and whether all possible words have been guessed.
     * @param checkingWord word to be checked.
     * @param checkedWords list of guessed and checked words.
     * @return check response.
     */
    @Transactional(readOnly = true)
    public Result check(String checkingWord, List<String> checkedWords) {
        CheckRequest checkRequest = new CheckRequest();
        checkRequest.setCheckingWord(checkingWord);
        checkRequest.setNumOfWords(Integer.parseInt(session(NUM_WORDS_SESSION)));
        String providedWord = session(PROVIDED_WORD_SESSION);

        CheckResponse checkResponse = new CheckResponse();
        if(providedWord != null) {
            checkRequest.setScrambledWord(providedWord);
            checkRequest.setCheckedWords(new ArrayList<>(checkedWords));
            checkResponse = this.wordService.checkWord(checkRequest);
            if(checkResponse.getNextCharacters() != null) {
                session(PROVIDED_WORD_SESSION, convertToString(checkResponse.getNextCharacters()));
                List<String> words = this.wordService.getAllWords(convertToString(checkResponse.getNextCharacters()));
                session(NUM_WORDS_SESSION, String.valueOf(words.size()));
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

    /**
     * Returns a list of shuffled characters from a random word in database.
     * @return a list of shuffled characters.
     */
    @Transactional(readOnly = true)
    public Result getCharacters(){
        CharactersResponse response = new CharactersResponse();
        List<Character> characters = this.wordService.getCharacters();
        response.setCharacters(characters);
        List<String> words = this.wordService.getAllWords(this.convertToString(characters));
        session(PROVIDED_WORD_SESSION,convertToString(response.getCharacters()));
        session(NUM_WORDS_SESSION,String.valueOf(words.size()));
        return ok(toJson(response));
    }

    /**
     * Returns all possible words from the original shuffled word saved in session.
     * @return a list of possible words.
     */
    @Transactional(readOnly = true)
    public Result getAllWords() {
        AllWordsResponse response = new AllWordsResponse();
        response.setWords(this.wordService.getAllWords(session(PROVIDED_WORD_SESSION)));
        return ok(toJson(response));
    }
}
