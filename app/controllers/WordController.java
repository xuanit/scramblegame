package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import play.libs.Json;
import play.mvc.*;
import services.WordService;
import views.html.*;

import javax.inject.Inject;

import java.util.List;

import static play.libs.Json.toJson;

/**
 * Created by xuan on 7/7/2016.
 */
public class WordController extends Controller {

    @Inject
    private WordService wordService;

    public WordController(WordService wordService) {
        this.wordService = wordService;
    }

    public Result check(String checkingWord, List<String> checkedWords) {
        CheckRequest checkRequest;
        try {
            JsonNode jsonNode = request().body().asJson();
            checkRequest = Json.fromJson(jsonNode, CheckRequest.class);
        }catch (Exception ex) {
            return badRequest();
        }
        CheckResponse checkResponse = this.wordService.checkWord(checkRequest);
        return ok(toJson(checkResponse));
    }
}
