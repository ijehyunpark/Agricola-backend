package com.semoss.agricola.GamePlay.domain.Action;

import com.semoss.agricola.GamePlay.domain.Player;
import com.semoss.agricola.GamePlay.domain.ResourceType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StackActionTest {
    Player player;
    StackAction stackAction;

    @BeforeEach
    void setUp() {
        player = new Player("test");
        stackAction = new StackAction(ResourceType.WOOD,3);
    }

    @Test
    void runAction() {
        stackAction.runAction();
        assertEquals(6,stackAction.getStackResource());
        stackAction.runAction(player);
        assertEquals(0,stackAction.getStackResource());
    }

    @Test
    void testRunAction() {
        stackAction.runAction(player);
        assertEquals(3,player.getResource(ResourceType.WOOD));
        stackAction.runAction();
        stackAction.runAction();
        stackAction.runAction(player);
        assertEquals(9, player.getResource(ResourceType.WOOD));
    }
}