package com.semoss.agricola.GamePlay.domain.card;

import java.util.HashMap;
import java.util.Map;

public class CardDictionary {
    public static Map<Long,Card> cardDict = new HashMap<>();;

    public static void init(){
        cardDict = new HashMap<>();
    }
    public static void addCard(Long id,Card card){
        cardDict.put(id,card);
    }
}
