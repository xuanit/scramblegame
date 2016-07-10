package repository;

import entity.Word;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import play.db.jpa.JPAApi;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * Created by xuan on 7/10/2016.
 */
@RunWith(MockitoJUnitRunner.class)
public class JPAWordRepositoryTest {

    private JPAWordRepository jpaWordRepository;

    @Mock
    private JPAApi jpaApi;

    @Mock
    private EntityManager entityManager;

    @Mock
    private Query query;

    @Before
    public void init() {
        this.jpaWordRepository = new JPAWordRepository(jpaApi);
        when(jpaApi.em()).thenReturn(entityManager);
        when(entityManager.createNativeQuery(anyString(), any(Class.class))).thenReturn(query);
        when(query.setParameter(anyString(), anyObject())).thenReturn(query);
    }

    @Test
    public void testFindByIndexWords(){
        List<String> wordStrings = Arrays.asList("a", "b");
        Collection<Word> words = this.jpaWordRepository.findByIndexWords(wordStrings);
        verify(entityManager).createNativeQuery(
                "SELECT word.* FROM word INNER JOIN (SELECT ? AS index_data UNION ALL SELECT ?) AS query_word ON word.index_data = query_word.index_data",
                Word.class);
        verify(query, times(wordStrings.size())).setParameter(anyInt(), anyString());
        verify(query).getResultList();
        assertNotNull(words);
    }
}
