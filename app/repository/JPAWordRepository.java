package repository;

import entity.Word;

import javax.inject.Singleton;
import java.util.List;

/**
 * Created by xuan on 7/8/2016.
 */
@Singleton
public class JPAWordRepository implements WordRepository{

    public Word findByWord(String word){
        return new Word();
    }

    @Override
    public List<Word> findByWords(List<String> words) {
        return null;
    }
}
