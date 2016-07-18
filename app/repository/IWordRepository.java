package repository;

import com.google.inject.ImplementedBy;
import entity.Word;

import java.util.Collection;

/**
 * Created by xuan on 7/8/2016.
 */
@ImplementedBy(WordRepository.class)
public interface IWordRepository {
    Word findByWord(String word);

    Collection<Word> findByIndexWords(Collection<String> indexWords);

    String getRandomWord();
}
