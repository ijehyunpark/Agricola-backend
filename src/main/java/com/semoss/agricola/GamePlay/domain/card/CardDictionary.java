package com.semoss.agricola.GamePlay.domain.card;

import java.util.HashMap;
import java.util.Map;

public class CardDictionary {
    public static Map<String,Card> cardDict;

    public static void init(){
        cardDict = new HashMap<>();
    }
}
