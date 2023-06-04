package com.semoss.agricola.GamePlay.domain.card;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.semoss.agricola.GamePlay.domain.card.Majorcard.MajorCard;
import com.semoss.agricola.GamePlay.domain.player.Player;
import lombok.Getter;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Getter
public class CardDictionary {
    @JsonIgnore
    public final Map<Long, Card> cardDict = new HashMap<>();
    public final Map<Long, Player> ownerDict = new HashMap<>();

    public CardDictionary(List<MajorCard> majorCards){
        // 주설비 카드 등록
        majorCards.forEach(majorCard -> cardDict.put(majorCard.getCardID(), majorCard));

        // TODO: 보조 설비 카드 등록

        // TODO: 직업 카드 등록

    }

    @JsonAlias("cardDict")
    public List<Card> getCardDictList() {
        return cardDict.values().stream().toList();
    }

    public void clear() {
        cardDict.clear();
        ownerDict.clear();
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

    /**
     * 해당 소유자가 가진 모든 카드를 찾는다.
     * @param player 카드 소유자
     * @return 해당 소유자가 가진 모든 카드의 식별자 리스트
     */
    public List<Long> findCardsByOwner(Player player) {
        List<Long> keys = new ArrayList<>();
        for (Map.Entry<Long, Player> entry : ownerDict.entrySet()) {
            if (entry.getValue().equals(player)) {
                keys.add(entry.getKey());
            }
        }
        return keys;
    }
}
