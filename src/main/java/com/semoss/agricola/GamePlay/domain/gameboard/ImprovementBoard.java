package com.semoss.agricola.GamePlay.domain.gameboard;

import com.semoss.agricola.GamePlay.domain.card.CardDictionary;
import com.semoss.agricola.GamePlay.domain.card.MajorCard;
import com.semoss.agricola.GamePlay.domain.card.Majorcard.MajorFactory;
import com.semoss.agricola.GamePlay.domain.resource.AnimalStruct;
import com.semoss.agricola.GamePlay.domain.player.AnimalType;
import com.semoss.agricola.GamePlay.domain.resource.ResourceStruct;
import com.semoss.agricola.GamePlay.domain.resource.ResourceType;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class ImprovementBoard {
    private final List<Long> majorCards; // TODO 삭제해야합니다

    public ImprovementBoard(CardDictionary cardDictionary){
        majorCards = new ArrayList<>();
        MajorFactory majorFactory = new MajorFactory();
        //화로
        cardDictionary.addCard(null,majorFactory.firePlace1());
        majorCards.add(1L);

        //화로
        cardDictionary.addCard(null, majorFactory.firePlace2());
        majorCards.add(2L);

        //화덕 (카드 반납은 구현 안돼있음)
        cardDictionary.addCard(null, majorFactory.firePlace3());
        majorCards.add(3L);

        //화덕 (카드 반납은 구현 안돼있음)
        cardDictionary.addCard(null, majorFactory.firePlace4());
        majorCards.add(4L);

        //흙가마 (더미) // 빵효율 증가만 적용
        cardDictionary.addCard(null, majorFactory.clayOven());
        majorCards.add(5L);

        //돌가마 (더미) // 빵효율 증가만 적용
        cardDictionary.addCard(null, majorFactory.stoneOven());
        majorCards.add(6L);

        //가구제작소
        cardDictionary.addCard(null, majorFactory.woodWorkShop());
        majorCards.add(7L);

        //그릇제작소
        cardDictionary.addCard(null, majorFactory.clayWorkShop());
        majorCards.add(8L);

        //바구니제작소
        cardDictionary.addCard(null, majorFactory.reedWorkShop());
        majorCards.add(9L);

        //우물 (더미) 능력 없음
        cardDictionary.addCard(null, majorFactory.well());
        majorCards.add(10L);
    }
}
