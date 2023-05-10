package com.semoss.agricola.GamePlay.domain.Action;

import com.semoss.agricola.GamePlay.domain.action.BakeAction;
import com.semoss.agricola.GamePlay.domain.player.Player;
import com.semoss.agricola.GamePlay.domain.resource.ResourceType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BakeActionTest {
    Player player;
    BakeAction bakeAction;

    @BeforeEach
    void setUp() {
        player = Player.builder()
                .userId(1234L)
                .isStartPlayer(true)
                .build();
        bakeAction = new BakeAction(2);
    }

    @Test
    void runAction() {
        assertFalse(bakeAction.checkPrecondition(player));
        player.addResource(ResourceType.GRAIN,2);
        assertTrue(bakeAction.checkPrecondition(player));

        bakeAction.runAction(player);
        assertEquals(2,player.getResource(ResourceType.FOOD));

        bakeAction.runAction(player);
        assertFalse(bakeAction.checkPrecondition(player));
    }
}