package speller.client;

/**
 * Created by Zlodiak on 22.09.2016.
 */

import java.net.URL;
import java.net.MalformedURLException;
import java.util.List;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;

import net.yandex.speller.services.spellservice.*;

public class SpellerWS_Client {
    public static void main(String[] args) throws MalformedURLException {
        URL url = new URL("http://localhost:9001/speller?wsdl");
        QName qname = new QName("http://ws.speller/", "SpellerWS_ImplService");
        Service service = Service.create(url, qname);
        SpellServiceSoap test = service.getPort(SpellServiceSoap.class);
        String text = "Проверка работы веб сервиса эмулирующего работу яндекс спеллера";
        CheckTextRequest request = new CheckTextRequest();
        request.setText(text);
        SpellResult response = test.checkText(request).getSpellResult();
          for (SpellError error : response.getError())
                System.out.println("text: " + error.getS() +" позиция ошибки: " + error.getRow()+":"+error.getCol());

        CheckTextsRequest request1 = new CheckTextsRequest();
        request1.getText().add(0,"Проверка,. работы... веб сервиса эмулирующего работу яндекс спеллера на основе всдл и еще много слов чтобы получить хотя бы одну ошибку в этом тексте");
        request1.getText().add(1,"И еще одна строка для проверки работы с массивом строк");
        List<SpellResult> response2 = test.checkTexts(request1).getArrayOfSpellResult().getSpellResult();
        int i=0;
        int j=0;

        for (SpellResult listerror  : response2)
        {
            System.out.println("----");
            for (SpellError error : listerror.getError())
                System.out.println("texts: " + error.getS() +" позиция ошибки: " + error.getRow()+":"+error.getCol());
        }
        System.out.println("finish");
     }
}
