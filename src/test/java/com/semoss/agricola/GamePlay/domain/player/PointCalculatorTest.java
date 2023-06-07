package com.semoss.agricola.GamePlay.domain.player;

import com.semoss.agricola.GamePlay.domain.AgricolaGame;
import com.semoss.agricola.GamePlay.domain.card.CardDictionary;
import com.semoss.agricola.GamePlay.domain.card.Majorcard.MajorCard;
import com.semoss.agricola.GamePlay.domain.card.Majorcard.MajorFactory;
import com.semoss.agricola.GamePlay.domain.card.Minorcard.DummyMinorCard;
import com.semoss.agricola.GamePlay.domain.card.Occupation.DummyOccupation;
import com.semoss.agricola.GamePlay.domain.resource.AnimalType;
import com.semoss.agricola.GamePlay.domain.resource.ResourceType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.ObjectProvider;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PointCalculatorTest {
    int[][][] fence1 = {{{0,3},{1,2},{1,4},{2,2},{2,4},{3,3}},{{0,3},{0,4},{1,2},{1,5},{2,3},{2,4}}};
    PointCalculator pointCalculator = new PointCalculator();
    MajorFactory majorFactory = new MajorFactory();
    MajorCard majorCard = majorFactory.woodWorkShop();
    CardDictionary cardDictionary;

    AgricolaGame game = mock(AgricolaGame.class);

    @Test
    void calculate() {
        List<MajorCard> majorCards = new ArrayList<>();
        majorCards.add(majorCard);
        ObjectProvider<DummyMinorCard> dummyMinorCards = mock(ObjectProvider.class);
        ObjectProvider<DummyOccupation> dummyOccupations = mock(ObjectProvider.class);
        cardDictionary = new CardDictionary(majorCards, new ArrayList<>(), dummyMinorCards, new ArrayList<>(), dummyOccupations);

        // given
        Player player = Player.builder().game(game).userId(123L).build();
        when(game.getCardDictionary()).thenReturn(cardDictionary);
        player.addResource(ResourceType.CLAY,7);
        player.addResource(ResourceType.STONE,2);
        player.addResource(ResourceType.WOOD,9);

        majorCard.place(player, cardDictionary);

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