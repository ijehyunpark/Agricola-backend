package com.semoss.agricola.GamePlay.domain.card;

import com.semoss.agricola.GamePlay.domain.History;
import com.semoss.agricola.GamePlay.domain.action.implement.ActionName;
import com.semoss.agricola.GamePlay.domain.card.Majorcard.MajorCard;
import com.semoss.agricola.GamePlay.domain.card.Majorcard.MajorFactory;
import com.semoss.agricola.GamePlay.domain.card.Minorcard.ActionMinorCard;
import com.semoss.agricola.GamePlay.domain.card.Minorcard.DummyMinorCard;
import com.semoss.agricola.GamePlay.domain.card.Minorcard.MinorFactory;
import com.semoss.agricola.GamePlay.domain.card.Occupation.DummyOccupation;
import com.semoss.agricola.GamePlay.domain.player.Player;
import com.semoss.agricola.GamePlay.domain.resource.ResourceType;
import com.semoss.agricola.GamePlay.exception.IllegalRequestException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.ObjectProvider;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class ActionTriggerTest {
    ActionMinorCard canoe;
    ActionMinorCard cornScoop;
    DummyMinorCard dummy1;
    DummyMinorCard dummy2;
    MinorFactory minorFactory = new MinorFactory();
    CardDictionary cardDictionary;
    Player player;
    History history;

    @BeforeEach
    void setUp() {
        ObjectProvider<DummyMinorCard> dummyMinorCards = mock(ObjectProvider.class);
        ObjectProvider<DummyOccupation> dummyOccupations = mock(ObjectProvider.class);
        List<MajorCard> majorCardList = new ArrayList<>();
        majorCardList.add(new MajorFactory().firePlace1());
        cardDictionary = new CardDictionary(majorCardList, new ArrayList<>(), dummyMinorCards, new ArrayList<>(), dummyOccupations);

        canoe = minorFactory.canoe();
        cornScoop = minorFactory.cornScoop();
        dummy1 = new DummyMinorCard();
        dummy2 = new DummyMinorCard();
        player = Player.builder().userId(1234L).isStartPlayer(true).build();
        cardDictionary.addCardInPlayerHand(player,dummy1);
        cardDictionary.addCardInPlayerHand(player,dummy2);
        cardDictionary.addCardInPlayerHand(player,canoe);
        cardDictionary.addCardInPlayerHand(player,cornScoop);
    }

    @Test
    @DisplayName("전제조건 동작 테스트")
    void preconditionTest() {
        //given
        player.addResource(ResourceType.WOOD,2);

        // when then
        assertThrows(IllegalRequestException.class, () -> canoe.place(player,cardDictionary,1));

        //given
        dummy1.place(player,cardDictionary,1);
        dummy2.place(player,cardDictionary,1);

        //when
        canoe.place(player,cardDictionary,2);

        //then
        assertEquals(player,cardDictionary.getOwner(canoe).get());
    }

    @Test
    @DisplayName("액션트리거 동작 테스트")
    void actionTrigger() {
        //given
        history = History.builder().eventName(ActionName.ACTION10).build();
        dummy1.place(player,cardDictionary,1);
        dummy2.place(player,cardDictionary,1);
        player.addResource(ResourceType.WOOD,3);
        canoe.place(player,cardDictionary,2);

        //when
        player.playAction(history, cardDictionary);

        //then
        assertEquals(1,player.getResource(ResourceType.FOOD));
        assertEquals(1,player.getResource(ResourceType.REED));

        //given
        history = History.builder().eventName(ActionName.ACTION3).build();
        cornScoop.place(player,cardDictionary,2);

        //when
        player.playAction(history, cardDictionary);

        //then
        assertEquals(1,player.getResource(ResourceType.GRAIN));
    }

    @Test
    @DisplayName("직업 카드 액션 트리거 테스트")
    void occupationActionTest(){

    }
}