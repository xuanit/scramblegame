package repository;

import entity.Word;
import play.db.jpa.JPAApi;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.persistence.Query;
import java.util.Collection;
import java.util.List;

/**
 * Created by xuan on 7/8/2016.
 */
@Singleton
public class WordRepository implements IWordRepository {

    private JPAApi jpaApi;

    @Inject
    public WordRepository(JPAApi jpa){
        this.jpaApi = jpa;
    }
    public Word findByWord(String word){
        List<Word> words = this.jpaApi.em().createQuery("SELECT word FROM Word word WHERE word.word = :word", Word.class)
                .setParameter("word", word).getResultList();
        if(words.size() > 1) {
            throw new IllegalArgumentException("More than one word found in db");
        }
        if(words.size() > 0) {
            return words.get(0);
        }else{
            return null;
        }
    }

    @Override
    public Collection<Word> findByIndexWords(Collection<String> indexWords) {
        if(indexWords.size() == 0) {
            throw new IllegalArgumentException("No index words to query");
        }
        StringBuilder builder = new StringBuilder();
        builder.append("SELECT word.* FROM word INNER JOIN (SELECT ? AS index_data");
        for(int index = 1; index < indexWords.size(); ++ index) {
            builder.append(" UNION ALL SELECT ?");
        }
        builder.append(") AS query_word ON word.index_data = query_word.index_data");
        Query query = this.jpaApi.em().createNativeQuery(builder.toString(), Word.class);
        int index = 1;
        for(String word : indexWords) {
            query.setParameter(index, word);
            ++index;
        }
        return query.getResultList();
    }

    @Override
    public String getRandomWord() {
        return this.jpaApi.em().createQuery("SELECT word FROM Word word ORDER BY rand()", Word.class).setMaxResults(1)
                .getSingleResult().getWord();

    }
}
