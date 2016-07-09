package controllers;

import java.util.List;

/**
 * Created by xuan on 7/9/2016.
 */
public class CharacterResponse {

    public List<Character> getCharacters() {
        return characters;
    }

    public void setCharacters(List<Character> characters) {
        this.characters = characters;
    }

    private List<Character> characters;
}
