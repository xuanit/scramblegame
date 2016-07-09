package repository;

import com.google.inject.ImplementedBy;
import entity.Word;

import java.util.List;

/**
 * Created by xuan on 7/8/2016.
 */
@ImplementedBy(JPAWordRepository.class)
public interface WordRepository {
    Word findByWord(String word);

    List<Word> findByWords(List<String> words);
}
