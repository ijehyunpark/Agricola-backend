package com.semoss.agricola.GamePlay.domain.card;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.semoss.agricola.GamePlay.domain.card.Majorcard.MajorCard;
import com.semoss.agricola.GamePlay.domain.card.Minorcard.DummyMinorCard;
import com.semoss.agricola.GamePlay.domain.card.Minorcard.MinorCard;
import com.semoss.agricola.GamePlay.domain.card.Occupation.DummyOccupation;
import com.semoss.agricola.GamePlay.domain.card.Occupation.Occupation;
import com.semoss.agricola.GamePlay.domain.player.Player;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.ObjectProvider;
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
@Log4j2
public class CardDictionary {
    @JsonIgnore
    public final Map<Long, Card> cardDict = new HashMap<>();
    public final Map<Long, Player> ownerDict = new HashMap<>();

    public CardDictionary(List<MajorCard> majorCards,
                          List<MinorCard> minorCards, ObjectProvider<DummyMinorCard> dummyMinorCards,
                          List<Occupation> occupations, ObjectProvider<DummyOccupation> dummyOccupations){
        log.debug("card 사전 생성: " + this.hashCode());
        // 주설비 카드 등록
        majorCards.forEach(majorCard -> cardDict.put(majorCard.getCardID(), majorCard));

        // 보조 설비 카드 등록
        int minorCardSize = minorCards.size();
        log.debug("보조 설비 카드 개수: " + minorCardSize);
        minorCards.forEach(minorCard -> cardDict.put(minorCard.getCardID(), minorCard));

        //보조 설비 더미 카드 생성
        for (int i = minorCardSize; i < 28; i++) {
            DummyMinorCard dummyMinorCard = dummyMinorCards.getObject();
            cardDict.put(dummyMinorCard.getCardID(), dummyMinorCard);
        }

        // 직업 카드 등록
        int occupationSize = occupations.size();
        log.debug("직업 카드 개수: " + occupationSize);
        occupations.forEach(occupation -> cardDict.put(occupation.getCardID(), occupation));

        //직업카드 더미 카드 생성
        for (int i = occupationSize; i < 28; i++) {
            DummyOccupation dummyOccupation = dummyOccupations.getObject();
            cardDict.put(dummyOccupation.getCardID(), dummyOccupation);
        }

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
