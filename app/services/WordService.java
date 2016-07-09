package services;

import controllers.CheckRequest;
import controllers.CheckResponse;
import entity.Word;
import play.db.jpa.JPAApi;
import repository.WordRepository;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.persistence.EntityManager;

/**
 * Created by xuan on 7/8/2016.
 */
@Singleton
public class WordService  implements IWordService {

    @Inject
    private WordRepository wordRepository;

    @Inject
    private WordGenerator wordGenerator;

    public WordRepository getWordRepository() {
        return wordRepository;
    }

    public void setWordRepository(WordRepository wordRepository) {
        this.wordRepository = wordRepository;
    }

    public CheckResponse checkWord(CheckRequest checkRequest) {
        Word word = this.wordRepository.findByWord(checkRequest.getCheckingWord());
        CheckResponse response = new CheckResponse();
        response.setCheckedWord(checkRequest.getCheckingWord());
        if(word != null) {
            response.setValid(true);
        }else{
            response.setValid(false);
        }
        return response;
    }
}
