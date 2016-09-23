package speller.endpoint;

/**
 * Created by Zlodiak on 22.09.2016.
 */

import speller.ws.SpellerWS_Impl;

import javax.xml.ws.Endpoint;

public class SpellerWS_Publisher {
    public static void main(String... args) {
        Endpoint.publish("http://localhost:9001/speller", new SpellerWS_Impl());
    }
}
