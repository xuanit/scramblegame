package service;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import services.WordGenerator;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by xuan on 7/8/2016.
 */
public class WordGeneratorTest {

    private WordGenerator wordGenerator;

    @Before
    public void init(){
        this.wordGenerator = new WordGenerator();
    }

    @Test
    public void testGenerateOnCharacter(){
        Set<String> words = this.wordGenerator.generate("a", 1);
        assertEquals(1, words.size());
        assertTrue(words.contains("a"));
    }

    @Test
    public void testGenerateTwoCharacters(){
        Set<String> words = this.wordGenerator.generate("ab", 2);
        assertEquals(4, words.size());
        assertTrue(words.contains("a"));
        assertTrue(words.contains("b"));
        assertTrue(words.contains("ab"));
        assertTrue(words.contains("ba"));
    }

    @Test
    public void testGenerateThreeCharacters(){
        Set<String> words = this.wordGenerator.generate("abc", 3);
        assertEquals(15, words.size());
    }

    @Test
    public void testGenerateWithMaxLengthOf2() {
        Set<String> words = this.wordGenerator.generate("abcd", 3);
        assertEquals(40, words.size());
    }

    @Test
    public void testCompeleteWordWithOneCharacter(){
        int length = 1;
        Set<String> words = this.wordGenerator.compeleteWord(new char[]{'b'}, new HashSet<Integer>(), length, new char[length]);
        assertEquals(1, words.size());
        assertTrue( words.contains("b"));
    }

    @Test
    public void testCompeleteWordWithTwoCharacters(){
        int length = 2;
        Set<String> words = this.wordGenerator.compeleteWord(new char[]{'a', 'b'},new HashSet<Integer>(),length, new char[length]);
        assertEquals(2, words.size());
        assertTrue( words.contains("ab"));
        assertTrue( words.contains("ba"));
    }

    @Test
    public void testCompeleteWordWithThreeCharacters(){
        int length = 3;
        Set<String> words = this.wordGenerator.compeleteWord(new char[]{'a', 'b', 'c'}, new HashSet<Integer>(),length, new char[length]);
        assertEquals(6, words.size());
    }

    @Test
    public void testCompeleteOneCharacterWordWithThreeCharacters(){
        int length = 3;
        Set<String> words = this.wordGenerator.compeleteWord(new char[]{'a', 'b', 'c'},new HashSet<Integer>(), 1, new char[1]);
        assertEquals(3, words.size());
        assertTrue( words.contains("a"));
        assertTrue( words.contains("b"));
        assertTrue( words.contains("c"));
    }

    @Test
    public void stressTestGenerate(){
        Set<String> words = this.wordGenerator.generate("123456789012345678901234567890", 4);
        System.out.print("Stress test");
        System.out.print(words.size());

    }

    @Test
    public void testGetShuffledCharacters(){
        String word = "word";
        List<Character> characters = this.wordGenerator.shuffleCharacters(word);
        assertEquals(word.length(), characters.size());
        StringBuilder builder = new StringBuilder(characters.size());
        for(Character ch: characters)
        {
            builder.append(ch);
        }
        assertNotEquals(word, builder.toString());
        for(Character character : characters) {
            assertTrue(word.contains(String.valueOf(character)));
        }
    }

}
