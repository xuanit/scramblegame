package services;

import java.util.List;

/**
 * Created by xuan on 7/8/2016.
 */
public class CheckRequest {

    private String checkingWord;

    private String scrambledWord;

    private List<String> checkedWords;

    public int getNumOfWords() {
        return numOfWords;
    }

    public void setNumOfWords(int numOfWords) {
        this.numOfWords = numOfWords;
    }

    private int numOfWords;

    public String getScrambledWord() {
        return scrambledWord;
    }

    public void setScrambledWord(String scrambledWord) {
        this.scrambledWord = scrambledWord;
    }

    public String getCheckingWord() {
        return checkingWord;
    }

    public void setCheckingWord(String checkingWord) {
        this.checkingWord = checkingWord;
    }

    public List<String> getCheckedWords() {
        return checkedWords;
    }

    public void setCheckedWords(List<String> checkedWords) {
        this.checkedWords = checkedWords;
    }
}
