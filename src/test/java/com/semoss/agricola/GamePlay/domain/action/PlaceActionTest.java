package com.semoss.agricola.GamePlay.domain.action;

import com.semoss.agricola.GamePlay.domain.card.CardType;
import com.semoss.agricola.GamePlay.domain.card.MajorCard;
import com.semoss.agricola.GamePlay.domain.player.AnimalStruct;
import com.semoss.agricola.GamePlay.domain.player.AnimalType;
import com.semoss.agricola.GamePlay.domain.player.Player;
import com.semoss.agricola.GamePlay.domain.resource.ResourceStruct;
import com.semoss.agricola.GamePlay.domain.resource.ResourceType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlaceActionTest {
    Player player;
    Player other;

    MajorCard majorCard;

    @BeforeEach
    void setUp() {
        player = Player.builder()
                .userId(1234L)
                .build();
        majorCard = MajorCard.builder()
                .cardID(1L)
                .bonusPoint(1)
                .ingredients(new ResourceStruct[]{ResourceStruct.builder().resource(ResourceType.CLAY).count(2).build()})
                .resourcesToFoodAnytime(new ResourceStruct[]{ResourceStruct.builder().resource(ResourceType.VEGETABLE).count(2).build()})
                .animalsToFoodAnytime(new AnimalStruct[]{AnimalStruct.builder().animal(AnimalType.SHEEP).count(2).build(),
                        AnimalStruct.builder().animal(AnimalType.WILD_BOAR).count(2).build(),
                        AnimalStruct.builder().animal(AnimalType.CATTLE).count(3).build()})
                .bakeEfficiency(2).build();
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
        placeAction.runAction(player, majorCard);

        // then
        assertTrue(player.hasCardInField(majorCard.getCardID()));
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
        assertThrows(RuntimeException.class, () -> placeAction.runAction(player, majorCard));

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
        assertThrows(RuntimeException.class, () -> placeAction.runAction(player, majorCard));

        // then
    }

    @Test
    @DisplayName("runAction: 다른 사람이 소유한 카드 내려놓기 - 실패")
    void runActionPrecondition4() {
        // given
        other = Player.builder()
                .userId(2345L)
                .build();
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
        placeAction.runAction(other, majorCard);


        // when
        assertThrows(RuntimeException.class, () -> placeAction.runAction(player, majorCard));

        // then
    }
}