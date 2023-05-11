package com.semoss.agricola.GamePlay.domain.Action;

import com.semoss.agricola.GamePlay.domain.action.IncreaseFamily;
import com.semoss.agricola.GamePlay.domain.player.FieldType;
import com.semoss.agricola.GamePlay.domain.player.Player;
import com.semoss.agricola.GamePlay.domain.resource.ResourceStruct;
import com.semoss.agricola.GamePlay.domain.resource.ResourceType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IncreaseFamilyTest {
    Player player;
    IncreaseFamily increaseFamily;

    @BeforeEach
    void setUp() {
        player = Player.builder()
                .userId(1234L)
                .isStartPlayer(true)
                .build();
        player.addResource(ResourceType.FAMILY,2);
    }

    @Test
    void runActionPrecondition() {
        increaseFamily = new IncreaseFamily(ResourceStruct.builder()
                .resource(ResourceType.FAMILY)
                .count(1)
                .build(), true);
        assertFalse(player.familyPrecondition());
        assertFalse(increaseFamily.runAction(player));
        player.buildField(0, 0, FieldType.ROOM);
        assertTrue(player.familyPrecondition());
        assertTrue(increaseFamily.runAction(player));
        assertEquals(3,player.getResource(ResourceType.FAMILY));
    }

    @Test
    void runActionNotPrecondition() {
        increaseFamily = new IncreaseFamily(ResourceStruct.builder()
                .resource(ResourceType.FAMILY)
                .count(1)
                .build(),false);
        assertTrue(increaseFamily.runAction(player));
        assertEquals(3,player.getResource(ResourceType.FAMILY));
        assertFalse(player.familyPrecondition());
        assertTrue(increaseFamily.runAction(player));
        assertEquals(4,player.getResource(ResourceType.FAMILY));
    }
}