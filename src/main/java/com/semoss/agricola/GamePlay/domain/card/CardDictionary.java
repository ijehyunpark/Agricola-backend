package com.semoss.agricola.GamePlay.domain.card;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.semoss.agricola.GamePlay.domain.card.Majorcard.MajorCard;
import com.semoss.agricola.GamePlay.domain.card.Minorcard.DummyMinorCard;
import com.semoss.agricola.GamePlay.domain.card.Minorcard.MinorCard;
import com.semoss.agricola.GamePlay.domain.card.Occupation.DummyOccupation;
import com.semoss.agricola.GamePlay.domain.card.Occupation.Occupation;
import com.semoss.agricola.GamePlay.domain.player.Player;
import com.semoss.agricola.GamePlay.exception.NotFoundException;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Getter
@Log4j2
public class CardDictionary {
    private final List<Card> cardDict = new ArrayList<>();
    private final Map<Card, Player> ownerDict = new HashMap<>();
    private final Map<Card, Player> playerHand = new HashMap<>();

    public CardDictionary(List<MajorCard> majorCards,
                          List<MinorCard> minorCards, ObjectProvider<DummyMinorCard> dummyMinorCards,
                          List<Occupation> occupations, ObjectProvider<DummyOccupation> dummyOccupations){
        log.debug("card 사전 생성: " + this.hashCode());
        // 주설비 카드 등록
        int majorCardSize = majorCards.size();
        cardDict.addAll(majorCards);

        // 보조 설비 카드 등록
        int minorCardSize = minorCards.size();
        cardDict.addAll(minorCards);

        //보조 설비 더미 카드 생성
        for (int i = minorCardSize; i < 28; i++) {
            DummyMinorCard dummyMinorCard = dummyMinorCards.getObject();
            cardDict.add(dummyMinorCard);
        }

        // 직업 카드 등록
        int occupationSize = occupations.size();
        cardDict.addAll(occupations);

        //직업카드 더미 카드 생성
        for (int i = occupationSize; i < 28; i++) {
            DummyOccupation dummyOccupation = dummyOccupations.getObject();
            cardDict.add(dummyOccupation);
        }

        log.debug("카드 등록 수(주설비, 보조설비, 직업): " + majorCardSize + ", " + minorCardSize + ", " + occupationSize);

    }

    /**
     * 해당 id를 가진 card를 찾는다.
     */
    public Card getCard(Long id) {
        return cardDict.stream()
                .filter(card -> Objects.equals(card.getCardID(), id))
                .findFirst()
                .orElseThrow(NotFoundException::new);
    }

    /**
     * 해당 플레이어가 가진 모든 card를 찾는다.
     */
    public List<Card> getCards(Player player) {
        return ownerDict.entrySet().stream()
                .filter(entry -> entry.getValue().equals(player))
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    /**
     * 카드를 플레이어의 필드에 내려놓는다.
     */
    public void place(Player player, Card card){
        this.ownerDict.put(card, player);
    }

    /**
     * 해당 card를 가진 card의 소유자를 찾는다.
     * @param card card
     * @return
     */
    public Optional<Player> getOwner(Card card){
        return Optional.ofNullable(ownerDict.get(card));
    }

    public void changeOwner(Card card, Player player){
        ownerDict.put(card,player);
    }

    /**
     * 해당 소유자가 가진 모든 카드를 찾는다.
     * @param player 카드 소유자
     * @return 해당 소유자가 가진 모든 카드의 식별자 리스트
     */
    public List<Card> findCardsByOwner(Player player) {
        List<Card> keys = new ArrayList<>();
        for (Map.Entry<Card, Player> entry : ownerDict.entrySet()) {
            if (entry.getValue().equals(player)) {
                keys.add(entry.getKey());
            }
        }
        return keys;
    }

    @JsonIgnore
    public List<MinorCard> getAllMinorCards() {
        return this.cardDict.stream()
                .filter(card -> card.getCardType() == CardType.MINOR)
                .map(card -> (MinorCard) card)
                .toList();
    }

    @JsonIgnore
    public List<Occupation> getAllOccupations() {
        return this.cardDict.stream()
                .filter(card -> card.getCardType() == CardType.OCCUPATION)
                .map(card -> (Occupation) card)
                .toList();
    }

    /**
     * 플레이어 손에 카드를 추가한다.
     */
    public void addCardInPlayerHand(Player player, Card card) {
        this.playerHand.put(card, player);
    }

    public List<Card> getCardInPlayerHand(Player player){
        return this.playerHand.entrySet().stream()
                .filter(cardPlayerEntry -> cardPlayerEntry.getValue().equals(player))
                .map(Map.Entry::getKey)
                .toList();
    }

    /**
     * 해당 플레이어가 카드를 가지고 있는지 검증
     */
    public boolean hasCardInField(Player player, Card card) {
        return this.ownerDict.get(card).equals(player);
    }
}
