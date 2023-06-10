package com.semoss.agricola.GamePlay.domain.action.component;

import com.semoss.agricola.GamePlay.domain.AgricolaGame;
import com.semoss.agricola.GamePlay.domain.card.CardDictionary;
import com.semoss.agricola.GamePlay.domain.card.CardType;
import com.semoss.agricola.GamePlay.domain.card.Majorcard.MajorCard;
import com.semoss.agricola.GamePlay.domain.card.Majorcard.MajorFactory;
import com.semoss.agricola.GamePlay.domain.card.Minorcard.DummyMinorCard;
import com.semoss.agricola.GamePlay.domain.card.Occupation.DummyOccupation;
import com.semoss.agricola.GamePlay.domain.player.Player;
import com.semoss.agricola.GamePlay.domain.resource.ResourceStruct;
import com.semoss.agricola.GamePlay.domain.resource.ResourceType;
import com.semoss.agricola.GamePlay.exception.IllegalRequestException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.ObjectProvider;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class PlaceActionTest {
    Player player;
    Player other;
    MajorCard majorCard;
    AgricolaGame game;
    CardDictionary cardDictionary;

    @BeforeEach
    void setUp() {
        game = mock(AgricolaGame.class);
        player = Player.builder()
                .build();
        MajorFactory majorFactory = new MajorFactory();
        List<MajorCard> majorCards = new ArrayList<>();
        majorCard = majorFactory.firePlace1();
        majorCards.add(majorCard);

        ObjectProvider<DummyMinorCard> dummyMinorCards = mock(ObjectProvider.class);
        ObjectProvider<DummyOccupation> dummyOccupations = mock(ObjectProvider.class);
        cardDictionary = new CardDictionary(majorCards, new ArrayList<>(), dummyMinorCards, new ArrayList<>(), dummyOccupations);
    }

    @Test
    @DisplayName("runAction: 카드 내려놓기 - 성공")
    void runActionPrecondition1() {
        // given
        PlaceAction placeAction = PlaceAction.builder()
                .cardType(CardType.MAJOR)
                .build();
        player.addResource(ResourceStruct.builder()
                .resource(ResourceType.CLAY)
                .count(2)
                .build());
        assertEquals(2, player.getResource(ResourceType.CLAY));


        // when
        placeAction.runAction(player, majorCard.getCardID(), cardDictionary, 0);

        // then
        assertTrue(cardDictionary.hasCardInField(player, majorCard));
        assertEquals(0, player.getResource(ResourceType.CLAY));
    }

    @Test
    @DisplayName("runAction: 비용 부족으로 카드 내려놓기 - 실패")
    void runActionPrecondition2() {
        // given
        PlaceAction placeAction = PlaceAction.builder()
                .cardType(CardType.MAJOR)
                .build();


        // when
        assertThrows(RuntimeException.class, () -> placeAction.runAction(player, majorCard.getCardID(), cardDictionary, 0));

        // then
    }

    @Test
    @DisplayName("runAction: 다른 영역의 카드로 인한 카드 내려놓기 - 실패")
    void runActionPrecondition3() {
        // given
        PlaceAction placeAction = PlaceAction.builder()
                .cardType(CardType.MINOR)
                .build();
        player.addResource(ResourceStruct.builder()
                .resource(ResourceType.CLAY)
                .count(2)
                .build());


        // when
        assertThrows(RuntimeException.class, () -> placeAction.runAction(player, majorCard.getCardID(), cardDictionary, 0));

        // then
    }

    @Test
    @DisplayName("runAction: 다른 사람이 소유한 카드 내려놓기 - 실패")
    void runActionPrecondition4() {
        // given
        other = Player.builder().build();
        PlaceAction placeAction = PlaceAction.builder()
                .cardType(CardType.MAJOR)
                .build();
        other.addResource(ResourceStruct.builder()
                .resource(ResourceType.CLAY)
                .count(2)
                .build());
        player.addResource(ResourceStruct.builder()
                .resource(ResourceType.CLAY)
                .count(2)
                .build());
        placeAction.runAction(other, majorCard.getCardID(), cardDictionary, 0);


        // when
        assertThrows(IllegalRequestException.class, () -> placeAction.runAction(player, majorCard.getCardID(), cardDictionary, 0));

        // then
    }
}