package service;

import controllers.CheckRequest;
import controllers.CheckResponse;

import entity.Word;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import repository.WordRepository;
import services.WordService;
import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

/**
 * Created by xuan on 7/8/2016.
 */
@RunWith(MockitoJUnitRunner.class)
public class WordServiceTest {

    @Mock
    private WordRepository wordRepository;

    private WordService wordService;

    @Before
    public void init() {
        this.wordService = new WordService();
        this.wordService.setWordRepository(wordRepository);
    }

    @Test
    public void testCheckExistingWord() {
        CheckRequest request = new CheckRequest();
        request.setCheckingWord("checkingWord");
        when(this.wordRepository.findByWord(request.getCheckingWord())).thenReturn(new Word());
        CheckResponse response = this.wordService.checkWord(request);
        assertNotNull(response);
        assertEquals(true, response.isValid());
        assertEquals(request.getCheckingWord(), response.getCheckedWord());
        assertEquals(null, response.getNextCharacters());
    }

    @Test
    public void testCheckNonexistingWord() {
        CheckRequest request = new CheckRequest();
        request.setCheckingWord("checkingWord");
        when(this.wordRepository.findByWord(request.getCheckingWord())).thenReturn(null);
        CheckResponse response = this.wordService.checkWord(request);
        assertNotNull(response);
        assertEquals(false, response.isValid());
    }
}
