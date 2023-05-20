package com.semoss.agricola.GamePlay.domain.action;

import com.semoss.agricola.GamePlay.domain.player.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

@Disabled
class BakeActionTest {
    Player player;
    BakeAction bakeAction;

    String improvement = "IMP-0";

    @BeforeEach
    void setUp() {
        player = Player.builder()
                .userId(1234L)
                .isStartPlayer(true)
                .build();
        bakeAction = BakeAction.builder().build();
    }

    @Test
    @Disabled
    void runAction() {
//        assertFalse(bakeAction.checkPrecondition(player, improvement));
//        player.addResource(ResourceType.GRAIN,2);
//        assertTrue(bakeAction.checkPrecondition(player, improvement));
//
//        bakeAction.runAction(player, improvement);
//        assertEquals(2,player.getResource(ResourceType.FOOD));
//
//        bakeAction.runAction(player, improvement);
//        assertFalse(bakeAction.checkPrecondition(player, improvement));
    }
}