package com.semoss.agricola.action;

import com.semoss.agricola.mainflow.Player;
import com.semoss.agricola.mainflow.ResourceType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IncreaseFamilyTest {
    Player player;
    IncreaseFamily increaseFamily;

    @BeforeEach
    void setUp() {
        player = new Player("test");
        player.addResource(ResourceType.FAMILY,2);
    }

    @Test
    void runActionPrecondition() {
        increaseFamily = new IncreaseFamily(ResourceType.FAMILY,1,true);
        assertFalse(player.familyPrecondition());
        assertFalse(increaseFamily.runAction(player));
        player.setRoomCount(3);
        assertTrue(player.familyPrecondition());
        assertTrue(increaseFamily.runAction(player));
        assertEquals(3,player.getResource(ResourceType.FAMILY));
    }

    @Test
    void runActionNotPrecondition() {
        increaseFamily = new IncreaseFamily(ResourceType.FAMILY,1,false);
        assertTrue(increaseFamily.runAction(player));
        assertEquals(3,player.getResource(ResourceType.FAMILY));
        assertFalse(player.familyPrecondition());
        assertTrue(increaseFamily.runAction(player));
        assertEquals(4,player.getResource(ResourceType.FAMILY));
    }
}