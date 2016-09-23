package speller.ws;

/**
 * Created by Zlodiak on 22.09.2016.
 */

import net.yandex.speller.services.spellservice.*;

import javax.jws.WebService;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@WebService(endpointInterface = "net.yandex.speller.services.spellservice.SpellServiceSoap")
public class SpellerWS_Impl implements SpellServiceSoap {

    private String generateErrors(String word) {
        String fixedWord=word;
        if (Math.random()*100<=5) {
            fixedWord=word.toUpperCase();
            if (fixedWord.equals(word)) fixedWord=word.toLowerCase();
        }
        return fixedWord;
    }

    private List<SpellError> mySpeller(String text,int row) {
        List<SpellError> result = new ArrayList<SpellError>();
        String[] words = text.split("[\\p{Punct}\\s\\t\\n\\r]+");
        List<String> listWords = Arrays.asList(words);
        int col=0;
        for (String word : listWords) {
            String fixedWord=generateErrors(word);
            if (!word.equals(fixedWord))
            {
                SpellError spellError = new SpellError();
                spellError.setWord(word);
                spellError.getS().add(fixedWord);
                spellError.setCode(1);
                spellError.setLen(word.length());
                spellError.setPos(1);
                spellError.setRow(row);
                spellError.setCol(col);
                result.add(spellError);
            }
            col++;
        }
        return result;
    }

    public CheckTextResponse checkText(CheckTextRequest request) {
        CheckTextResponse checkTextResponse = new CheckTextResponse();
        SpellResult spellResult = new SpellResult();
        String text = request.getText();
        for (SpellError spellError : mySpeller(text,0)) {
            spellResult.getError().add(spellError);
        }
        checkTextResponse.setSpellResult(spellResult);
        return checkTextResponse;
    }

    public CheckTextsResponse checkTexts(CheckTextsRequest request) {
        CheckTextsResponse checkTextsResponse = new CheckTextsResponse();
        ArrayOfSpellResult arrayOfSpellResult = new ArrayOfSpellResult();
        List<String> texts = request.getText();
        int j=0;
        for (String text : texts) {
            SpellResult spellResult = new SpellResult();
            for (SpellError spellError : mySpeller(text,j)) {
                spellResult.getError().add(spellError);
            }
            arrayOfSpellResult.getSpellResult().add(spellResult);
            j++;
        }
        checkTextsResponse.setArrayOfSpellResult(arrayOfSpellResult);
        return checkTextsResponse;
    }
}
