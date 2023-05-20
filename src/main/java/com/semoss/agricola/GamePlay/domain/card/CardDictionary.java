package com.semoss.agricola.GamePlay.domain.card;

import java.util.HashMap;
import java.util.Map;

public class CardDictionary {
    public Map<Long,Card> cardDict = new HashMap<>();;

    public void init(){
        cardDict = new HashMap<>();
    }
    public void addCard(Long id,Card card){
        cardDict.put(id,card);
    }
    public Card getCard(Long id) {return cardDict.get(id); }
}
