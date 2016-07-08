package controllers;

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

    public String getNextWord() {
        return nextWord;
    }

    public void setNextWord(String nextWord) {
        this.nextWord = nextWord;
    }

    private String nextWord;
}
