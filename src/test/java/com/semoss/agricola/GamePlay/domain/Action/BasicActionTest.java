package com.semoss.agricola.GamePlay.domain.Action;

import com.semoss.agricola.GamePlay.domain.Player;
import com.semoss.agricola.GamePlay.domain.ResourceType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BasicActionTest {
    Player player;
    BasicAction basicAction;

    @BeforeEach
    void setUp() {
        player = new Player("test");
    }

    @Test
    void runAction() {
        assertEquals(0,player.getResource(ResourceType.WOOD));
        basicAction = new BasicAction(ResourceType.WOOD,3);
        basicAction.runAction(player);
        assertEquals(3,player.getResource(ResourceType.WOOD));
        basicAction.runAction(player);
        assertEquals(6,player.getResource(ResourceType.WOOD));
    }
}