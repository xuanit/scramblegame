package controllers;

import java.util.List;

/**
 * Created by xuan on 7/10/2016.
 */
public class AllWordsResponse {

    public List<String> getWords() {
        return words;
    }

    public void setWords(List<String> words) {
        this.words = words;
    }

    private List<String> words;
}
