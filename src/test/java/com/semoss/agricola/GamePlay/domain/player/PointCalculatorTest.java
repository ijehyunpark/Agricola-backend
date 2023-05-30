package com.semoss.agricola.GamePlay.domain.player;

import com.semoss.agricola.GamePlay.domain.AgricolaGame;
import com.semoss.agricola.GamePlay.domain.card.CardDictionary;
import com.semoss.agricola.GamePlay.domain.card.MajorCard;
import com.semoss.agricola.GamePlay.domain.resource.ResourceStruct;
import com.semoss.agricola.GamePlay.domain.resource.ResourceType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PointCalculatorTest {
    int[][][] fence1 = {{{0,3},{1,2},{1,4},{2,2},{2,4},{3,3}},{{0,3},{0,4},{1,2},{1,5},{2,3},{2,4}}};
    PointCalculator pointCalculator = new PointCalculator();
    MajorCard majorCard = MajorCard.builder()
            .cardID(7L)
            .bonusPoint(2)
            .ingredients(new ResourceStruct[]{ResourceStruct.builder().resource(ResourceType.WOOD).count(2).build(),
                    ResourceStruct.builder().resource(ResourceType.STONE).count(2).build()})
            .resourceToFoodHarvest(ResourceStruct.builder().resource(ResourceType.CLAY).count(2).build())
            .resourceTypeToPoints(ResourceType.CLAY)
            .resourceNumToPoints(new int[][]{{3,1},{5,2},{7,3}}).build();
    CardDictionary cardDictionary;

    AgricolaGame game = mock(AgricolaGame.class);

    @Test
    void calculate() {
        cardDictionary = new CardDictionary();
        cardDictionary.addCard(null,majorCard);
        // given
        Player player = Player.builder().game(game).userId(123L).build();
        when(game.getCardDictionary()).thenReturn(cardDictionary);
        player.addResource(ResourceType.CLAY,7);
        player.addResource(ResourceType.STONE,2);
        player.addResource(ResourceType.WOOD,2);

        majorCard.place(player);

        player.buildField(2,1,FieldType.ROOM);
        player.buildField(0,4,FieldType.FARM);
        player.upgradeRoom();
        player.buildFence(fence1[0],fence1[1]);
        player.addStable(1,3);
        player.addStable(1,2);
        player.getPlayerBoard().addAnimal(AnimalType.CATTLE,3);
        int expectPoint = 11;

        //when
        int actualPoint = pointCalculator.calculate(player,cardDictionary);

        //then
        assertEquals(expectPoint,actualPoint);
    }
}