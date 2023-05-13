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
    }

    @Test
    void runActionPrecondition() {
        increaseFamily = IncreaseFamily.builder()
                .precondition(true)
                .build();
        assertFalse(player.familyPrecondition());
        assertFalse(increaseFamily.runAction(player));
        player.buildField(0, 0, FieldType.ROOM);
        assertTrue(player.familyPrecondition());
        assertTrue(increaseFamily.runAction(player));
        assertEquals(3,player.getFamilyNumber());
    }

    @Test
    void runActionNotPrecondition() {
        increaseFamily = IncreaseFamily.builder()
                .precondition(false)
                .build();
        assertTrue(increaseFamily.runAction(player));
        assertEquals(3,player.getFamilyNumber());
        assertFalse(player.familyPrecondition());
        assertTrue(increaseFamily.runAction(player));
        assertEquals(4,player.getFamilyNumber());
    }
}