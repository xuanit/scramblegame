package repository;

import com.google.inject.ImplementedBy;
import entity.Word;

import java.util.Collection;
import java.util.List;

/**
 * Created by xuan on 7/8/2016.
 */
@ImplementedBy(JPAWordRepository.class)
public interface WordRepository {
    Word findByWord(String word);

    Collection<Word> findByIndexWords(Collection<String> indexWords);

    String getRandomWord();
}
