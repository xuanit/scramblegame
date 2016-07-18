package services;

import java.util.List;

/**
 * Created by xuan on 7/7/2016.
 */
public class CheckResponse {

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    private boolean valid;

    public String getCheckedWord() {
        return checkedWord;
    }

    public void setCheckedWord(String checkedWord) {
        this.checkedWord = checkedWord;
    }

    private String checkedWord;

    public List<Character> getNextCharacters() {
        return nextCharacters;
    }

    public void setNextCharacters(List<Character> nextCharacters) {
        this.nextCharacters = nextCharacters;
    }

    private List<Character> nextCharacters;

    public int getNumOfWords() {
        return numOfWords;
    }

    public void setNumOfWords(int numOfWords) {
        this.numOfWords = numOfWords;
    }

    private int numOfWords;
}
