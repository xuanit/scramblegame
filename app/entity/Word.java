package entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by xuan on 7/8/2016.
 */
@Entity
@Table(name = "word")
public class Word{

    public Word() {

    }

    public Word(String word, String indexWord) {
        this.word = word;
        this.indexWord = indexWord;
    }
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Id
    private long id;

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getIndexWord() {
        return indexWord;
    }

    public void setIndexWord(String indexWord) {
        this.indexWord = indexWord;
    }

    @Column(name = "word")
    private String word;

    @Column(name = "index_data")
    private String indexWord;
}
