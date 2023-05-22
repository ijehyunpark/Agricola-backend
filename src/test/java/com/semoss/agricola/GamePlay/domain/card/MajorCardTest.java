package com.semoss.agricola.GamePlay.domain.card;

import com.semoss.agricola.GamePlay.domain.gameboard.ImprovementBoard;
import com.semoss.agricola.GamePlay.domain.player.Player;
import com.semoss.agricola.GamePlay.domain.resource.ResourceStruct;
import com.semoss.agricola.GamePlay.domain.resource.ResourceType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MajorCardTest {
    ImprovementBoard improvementBoard = new ImprovementBoard();

    @Test
    void checkPrerequisites() {
        //given
        MajorCard oven = improvementBoard.getCard(0);
        MajorCard basket = improvementBoard.getCard(8);

        Player player1 = Player.builder().userId(123L).build();
        player1.addResource(ResourceStruct.builder().resource(ResourceType.CLAY).count(2).build());

        int expectClay = 0;
        boolean expectResult = false;
        Long expectOwner = 123L;

        //when
        oven.useResource(player1);
        int actualClay = player1.getResource(ResourceType.CLAY);
        boolean actualResult = basket.checkPrerequisites(player1);
        Long actualOwner = oven.getOwner();

        //then
        assertEquals(expectClay,actualClay);
        assertEquals(expectOwner,actualOwner);
        assertEquals(expectResult,actualResult);
    }

    @Test
    void checkPoint() {
        //given
        MajorCard oven = improvementBoard.getCard(0);
        MajorCard basket = improvementBoard.getCard(8);

        Player player1 = Player.builder().userId(123L).build();
        player1.addResource(ResourceStruct.builder().resource(ResourceType.REED).count(5).build());

        int expectPointOven = 0;
        int expectPointBasket = 2;

        //when
        int actualPointOven = oven.checkPoint(player1);
        int actualPointBasket = basket.checkPoint(player1);

        //then
        assertEquals(expectPointOven,actualPointOven);
        assertEquals(expectPointBasket,actualPointBasket);
    }
}