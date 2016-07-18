package services;

import com.google.inject.ImplementedBy;
import org.apache.commons.lang3.text.WordUtils;

import java.util.List;
import java.util.Set;

/**
 * Created by xuan on 7/10/2016.
 */
@ImplementedBy(WordUtils.class)
public interface IWordUtils {
    /**
     * Generates all combinations in string form of 1..maxLength characters from the word.
     * @param word word containing characters for generating combinations.
     * @param maxLength max length of the combinations.
     * @return a list of strings which are combinations of the characters from the words.
     */
    Set<String> generate(String word, int maxLength);

    /**
     * Shuffles and returns a list of characters from the word.
     * @param word word containing characters to be shuffled.
     * @return a list of shuffled characters from the word.
     */
    List<Character> shuffleCharacters(String word);
}
