package com.semoss.agricola.GamePlay.domain.action.component;

import com.semoss.agricola.GamePlay.domain.AgricolaGame;
import com.semoss.agricola.GamePlay.domain.card.BakeTrigger;
import com.semoss.agricola.GamePlay.domain.card.CardDictionary;
import com.semoss.agricola.GamePlay.domain.card.Majorcard.MajorCard;
import com.semoss.agricola.GamePlay.domain.card.Majorcard.MajorFactory;
import com.semoss.agricola.GamePlay.domain.card.Minorcard.DummyMinorCard;
import com.semoss.agricola.GamePlay.domain.card.Occupation.DummyOccupation;
import com.semoss.agricola.GamePlay.domain.player.Player;
import com.semoss.agricola.GamePlay.domain.resource.ResourceType;
import com.semoss.agricola.GamePlay.exception.NotFoundException;
import com.semoss.agricola.GamePlay.exception.ResourceLackException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.ObjectProvider;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class BakeActionTest {
    Player player;
    BakeAction bakeAction;
    BakeTrigger majorCard;
    AgricolaGame game;
    CardDictionary cardDictionary;

    @BeforeEach
    void setUp() {
        game = mock(AgricolaGame.class);
        player = Player.builder()
                .game(game)
                .userId(1234L)
                .isStartPlayer(true)
                .build();
        bakeAction = BakeAction.builder()
                .build();
        MajorFactory majorFactory = new MajorFactory();
        List<MajorCard> majorCards = new ArrayList<>();
        majorCard = majorFactory.firePlace1();
        majorCards.add((MajorCard) majorCard);

        ObjectProvider<DummyMinorCard> dummyMinorCards = mock(ObjectProvider.class);
        ObjectProvider<DummyOccupation> dummyOccupations = mock(ObjectProvider.class);
        cardDictionary = new CardDictionary(majorCards, new ArrayList<>(), dummyMinorCards, new ArrayList<>(), dummyOccupations);
    }

    @Test
    @DisplayName("빵 굽기 테스트 : 성공")
    void runAction() {
        // given
        cardDictionary.place(player, majorCard);
        player.addResource(ResourceType.GRAIN, 1);

        // when
        bakeAction.runAction(player, majorCard, cardDictionary);

        // then
        assertEquals(2, player.getResource(ResourceType.FOOD));
    }

    @Test
    @DisplayName("자원 부족으로 빵 굽기 테스트 : 실패")
    void runAction2() {
        // given
        cardDictionary.place(player, majorCard);

        // when
        assertThrows(
                ResourceLackException.class, () -> {
                    bakeAction.runAction(player, majorCard, cardDictionary);
                }
        );

        // then

    }

    @Test
    @DisplayName("주설비 없이 빵 굽기 테스트 : 실패")
    void runAction3() {
        // given
        player.addResource(ResourceType.GRAIN, 1);

        // when
        assertThrows(
                NotFoundException.class, () -> {
                    bakeAction.runAction(player, majorCard, cardDictionary);
                }
        );

        // then

    }
}