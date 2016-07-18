package services;

import com.google.inject.ImplementedBy;
import repository.WordRepository;

import java.util.List;

/**
 * Created by xuan on 7/8/2016.
 */
@ImplementedBy(WordService.class)
public interface IWordService {
    /**
     * Checks whether a guessing word in the check request is valid and whether all words made of from the characters are guessed.
     * If all words made of from the characters are guessed, an list of characters of a new random word is returned.
     * @param checkRequest check request containing checking word which need to be validated,
     *                     the original word which contains characters for creating all other words and a list of checked words
     *                     which has been checked.
     * @return check response containing check result and a list of characters of a new random words if necessary.
     */
    CheckResponse checkWord(CheckRequest checkRequest);

    /**
     * Gets all words generated from characters of the provided words.
     * @param providedWord a word containing characters that all generated words are made of.
     * @return list of words.
     */
    List<String> getAllWords(String providedWord);

    /**
     * Returns a list of shuffled characters from a random word from db.
     * @return a list of shuffled characters.
     */
    List<Character> getCharacters();
}
