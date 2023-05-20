package com.semoss.agricola.GamePlay.domain.card;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CardDictionary {
    public Map<Long,Card> cardDict = new HashMap<>();

    @JsonAlias("cardDict")
    public List<Card> getCardDictList(){
        return cardDict.values().stream().toList();
    }
    @JsonIgnore
    public Map<Long,Card> getCardDict() {
        return cardDict;
    }

    public void clear(){
        cardDict.clear();
    }
    public void addCard(Long id,Card card){
        cardDict.put(id,card);
    }
    public Card getCard(Long id) {return cardDict.get(id); }
}
