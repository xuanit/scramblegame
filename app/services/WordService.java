package services;

import entity.Word;
import repository.IWordRepository;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.*;

/**
 * Created by xuan on 7/8/2016.
 */
@Singleton
public class WordService  implements IWordService {

    public static final int MAX_LENGTH = 3;

    private IWordRepository wordRepository;

    private WordUtils wordGenerator;

    @Inject
    public WordService(IWordRepository wordRepository, WordUtils wordGenerator) {
        this.wordRepository = wordRepository;
        this.wordGenerator = wordGenerator;
    }


    /**
     *@inheritDoc
     */
    public CheckResponse checkWord(CheckRequest checkRequest) {
        Word word = this.wordRepository.findByWord(checkRequest.getCheckingWord());
        CheckResponse response = new CheckResponse();
        response.setCheckedWord(checkRequest.getCheckingWord());
        if(word != null) {
            response.setValid(true);
            checkRequest.getCheckedWords().add(checkRequest.getCheckingWord());
            if(checkRequest.getCheckedWords().size() == checkRequest.getNumOfWords()) {
                List<String> words = getAllWords(checkRequest.getScrambledWord());
                if(words.size() == checkRequest.getCheckedWords().size()) {
                    Collections.sort(words);
                    Collections.sort(checkRequest.getCheckedWords());
                    if(words.equals(checkRequest.getCheckedWords())) {
                        response.setNextCharacters(this.getCharacters());
                    }
                }
            }
        }else{
            response.setValid(false);
        }
        return response;
    }

    /**
     * @inheritDoc
     */
    public List<String> getAllWords(String providedWord) {
        Set<String> combinations = this.wordGenerator.generate(providedWord, MAX_LENGTH);
        List<Word> words = new ArrayList<>();
        char[] sortedProvidedCharacters = providedWord.toCharArray();
        Arrays.sort(sortedProvidedCharacters);
        List<String> matchedWordStrings = new ArrayList<>();
        words.addAll(this.wordRepository.findByIndexWords(combinations));
        for(Word word : words) {
            char[] sortedCharacters = word.getWord().toCharArray();
            int numOfMatches = 0;
            Arrays.sort(sortedCharacters);
            int charIndex = 0;
            int providedCharIndex = 0;
            while(charIndex < sortedCharacters.length && providedCharIndex < sortedProvidedCharacters.length) {
                char checkingChar = sortedCharacters[charIndex];
                while (providedCharIndex < sortedProvidedCharacters.length ) {
                    if(sortedProvidedCharacters[providedCharIndex] == checkingChar) {
                        numOfMatches++;
                        break;
                    }
                    providedCharIndex++;
                }
                providedCharIndex++;
                charIndex++;
            }
            if(numOfMatches == sortedCharacters.length) {
                matchedWordStrings.add(word.getWord());
            }
        }
        return matchedWordStrings;
    }

    /**
     * @inheritDoc
     */
    public List<Character> getCharacters(){
        String word = this.wordRepository.getRandomWord();
        return this.wordGenerator.shuffleCharacters(word);
    }

}
