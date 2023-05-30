package com.semoss.agricola.GamePlay.domain.card;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.semoss.agricola.GamePlay.domain.player.Player;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CardDictionary {
    public Map<Long, Card> cardDict = new HashMap<>();
    public Map<Long, Player> ownerDict = new HashMap<>();

    @JsonAlias("cardDict")
    public List<Card> getCardDictList() {
        return cardDict.values().stream().toList();
    }

    @JsonIgnore
    public Map<Long, Card> getCardDict() {
        return cardDict;
    }

    public void clear() {
        cardDict.clear();
    }

    public void addCard(Player player, Card card) {
        cardDict.put(card.getCardID(), card);
        ownerDict.put(card.getCardID(), player);
    }

    public Card getCard(Long id) {
        return cardDict.get(id);
    }

    public List<Long> getCards(Player player) {
        return ownerDict.entrySet().stream()
                .filter(entry -> entry.getValue().equals(player))
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    public Player getOwner(Long id){ return ownerDict.get(id); }

    public void changeOwner(Long cardID, Player player){
        ownerDict.put(cardID,player);
    }
}
