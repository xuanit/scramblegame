package services;

import javax.inject.Singleton;
import java.util.*;

/**
 * Created by xuan on 7/8/2016.
 */
@Singleton
public class WordGenerator {
    public Set<String> generate(String scrambledWord){
        char[] characters = scrambledWord.toCharArray();
        Set<String> words = new HashSet<String>();
        for(int length = 1; length <= characters.length; ++length) {
            words.addAll(compeleteWord(characters, new HashSet<Integer>(), length, new char[length]));
        }
        return words;
    }

    public Set<String> compeleteWord(char[] providedChars, Set<Integer> addedIndices, int length, char[] selectedChars) {
        if(length == 0) {
            return new HashSet<String>(Arrays.asList(new String(selectedChars)));
        }
        Set<String> words = new HashSet<String>();
        for(int index = 0; index < providedChars.length; ++index) {
            if(!addedIndices.contains(index)) {
                selectedChars[selectedChars.length - length] = providedChars[index];
                addedIndices.add(index);
                Set<String> newWords = compeleteWord(providedChars, addedIndices, length - 1, selectedChars);
                words.addAll(newWords);
                addedIndices.remove(index);
            }
        }
        return words;
    }
}
