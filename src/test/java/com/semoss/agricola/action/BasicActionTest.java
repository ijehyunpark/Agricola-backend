package com.semoss.agricola.action;

import com.semoss.agricola.mainflow.Player;
import com.semoss.agricola.mainflow.ResourceType;
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
        assertEquals(player.getResource(ResourceType.WOOD),0);
        basicAction = new BasicAction(ResourceType.WOOD,3);
        basicAction.runAction(player);
        assertEquals(player.getResource(ResourceType.WOOD),3);
    }
}