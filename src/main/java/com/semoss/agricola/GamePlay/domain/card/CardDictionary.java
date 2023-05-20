package com.semoss.agricola.GamePlay.domain.card;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CardDictionary {
    public Map<Long,Card> cardDict = new HashMap<>();

    public List<Card> getCardDict(){
        return cardDict.values().stream().toList();
    }

    public void clear(){
        cardDict.clear();
    }
    public void addCard(Long id,Card card){
        cardDict.put(id,card);
    }
    public Card getCard(Long id) {return cardDict.get(id); }
}
