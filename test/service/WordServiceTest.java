package service;

import com.google.common.collect.Lists;
import controllers.CheckRequest;
import controllers.CheckResponse;

import entity.Word;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import repository.WordRepository;
import services.WordGenerator;
import services.WordService;

import java.util.*;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

/**
 * Created by xuan on 7/8/2016.
 */
@RunWith(MockitoJUnitRunner.class)
public class WordServiceTest {

    @Mock
    private WordRepository wordRepository;

    @Mock
    private WordGenerator wordGenerator;

    private WordService wordService;

    @Before
    public void init() {
        this.wordService = new WordService(this.wordRepository, this.wordGenerator);
    }

    @Test
    public void testCheckValidWordWithoutNextCharacters() {
        Set<String> combinations = new HashSet<String>(Arrays.asList("a", "n", "an", "na"));
        List<Word> words = new ArrayList<>();
        for(String wordString : Arrays.asList("an", "a")) {
            Word word = new Word();
            word.setWord(wordString);
            word.setIndexWord(wordString);
            words.add(word);
        }
        CheckRequest request = new CheckRequest();
        request.setScrambledWord("an");
        request.setCheckedWords(new ArrayList<String>());
        request.setCheckingWord("a");
        when(this.wordRepository.findByWord(request.getCheckingWord())).thenReturn(new Word());

        when(this.wordGenerator.generate(request.getScrambledWord(), WordService.MAX_LENGTH)).thenReturn(combinations);
        when(this.wordRepository.findByIndexWords(any())).thenReturn(new ArrayList<Word>(words));
        when(this.wordService.getCharacters()).thenReturn(Lists.charactersOf("ab"));
        CheckResponse response = this.wordService.checkWord(request);
        assertNotNull(response);
        assertNull(response.getNextCharacters());
        assertEquals(true, response.isValid());
        assertEquals(request.getCheckingWord(), response.getCheckedWord());
        assertEquals(null, response.getNextCharacters());
        verify(this.wordGenerator).generate(request.getScrambledWord(), WordService.MAX_LENGTH);
    }

    @Test
    public void testCheckValidWordAndNextCharacter() {
        Set<String> combinations = new HashSet<String>(Arrays.asList("a", "n", "an", "na"));
        List<Word> words = new ArrayList<>();
        for(String wordString : Arrays.asList("an", "a", "ann")) {
            Word word = new Word();
            word.setWord(wordString);
            word.setIndexWord(wordString);
            words.add(word);
        }
        CheckRequest request = new CheckRequest();
        request.setScrambledWord("an");
        request.setCheckedWords(new ArrayList<String>());
        request.getCheckedWords().addAll(Arrays.asList("a"));
        request.setCheckingWord("an");
        when(this.wordRepository.findByWord(request.getCheckingWord()))
                .thenReturn(new Word(request.getCheckingWord(), request.getCheckingWord()));

        when(this.wordGenerator.generate(request.getScrambledWord(), WordService.MAX_LENGTH)).thenReturn(combinations);
        when(this.wordRepository.findByIndexWords(any())).thenReturn(new ArrayList<Word>(words));
        when(this.wordService.getCharacters()).thenReturn(Lists.charactersOf("abc"));
        CheckResponse response = this.wordService.checkWord(request);
        assertNotNull(response);
        assertNotNull(response.getNextCharacters());
        assertEquals(true, response.isValid());
        assertEquals(request.getCheckingWord(), response.getCheckedWord());
    }



    @Test
    public void testCheckValidWordAndNextCharacterForLongWord() {
        Set<String> combinations = new HashSet<String>(Arrays.asList("a"));
        List<Word> words = new ArrayList<>();
        for(String wordString : Arrays.asList("an", "a", "apple")) {
            Word word = new Word();
            word.setWord(wordString);
            word.setIndexWord(wordString.substring(0, 1));
            words.add(word);
        }
        CheckRequest request = new CheckRequest();
        request.setScrambledWord("an");
        request.setCheckedWords(new ArrayList<String>());
        request.getCheckedWords().addAll(Arrays.asList("a"));
        request.setCheckingWord("an");
        when(this.wordRepository.findByWord(request.getCheckingWord()))
                .thenReturn(new Word(request.getCheckingWord(), request.getCheckingWord()));

        when(this.wordGenerator.generate(request.getScrambledWord(), WordService.MAX_LENGTH)).thenReturn(combinations);
        when(this.wordRepository.findByIndexWords(any())).thenReturn(words);
        when(this.wordService.getCharacters()).thenReturn(Lists.charactersOf("abc"));
        CheckResponse response = this.wordService.checkWord(request);
        assertNotNull(response);
        assertNotNull(response.getNextCharacters());
        assertEquals(true, response.isValid());
        assertEquals(request.getCheckingWord(), response.getCheckedWord());
    }

    @Test
    public void testCheckInvalidWord() {
        CheckRequest request = new CheckRequest();
        request.setCheckingWord("checkingWord");
        when(this.wordRepository.findByWord(request.getCheckingWord())).thenReturn(null);
        CheckResponse response = this.wordService.checkWord(request);
        assertNotNull(response);
        assertEquals(false, response.isValid());
    }

    @Test
    public void testGetWord(){
        String wordFromDB = "Word";
        when(this.wordRepository.getRandomWord()).thenReturn(wordFromDB);
        String shuffledWord = "rodW";
        when(this.wordGenerator.shuffleCharacters(wordFromDB)).thenReturn(Lists.charactersOf(shuffledWord));
        List<Character> characters = this.wordService.getCharacters();
        assertEquals(Lists.charactersOf(shuffledWord), characters);
    }

}
