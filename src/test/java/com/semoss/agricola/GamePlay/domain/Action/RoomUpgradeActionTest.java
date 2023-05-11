package com.semoss.agricola.GamePlay.domain.Action;

import com.semoss.agricola.GamePlay.domain.action.RoomUpgradeAction;
import com.semoss.agricola.GamePlay.domain.player.FieldType;
import com.semoss.agricola.GamePlay.domain.player.Player;
import com.semoss.agricola.GamePlay.domain.resource.ResourceType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RoomUpgradeActionTest {
    Player player;
    RoomUpgradeAction roomUpgradeAction;

    @BeforeEach
    void setUp(){
        player = Player.builder()
                .userId(1234L)
                .isStartPlayer(true)
                .build();
        roomUpgradeAction = new RoomUpgradeAction(ResourceType.REED,2,1);
    }

    @Test
    void checkPrecondition() {
        player.upgradeRoom();
        assertEquals(FieldType.CLAY,player.getRoomType());
        assertFalse(roomUpgradeAction.checkPrecondition(player));

        player.addResource(ResourceType.REED,4);
        player.addResource(ResourceType.STONE,4);
        assertTrue(roomUpgradeAction.checkPrecondition(player));

        player.upgradeRoom();
        assertEquals(FieldType.STONE,player.getRoomType());
        assertFalse(roomUpgradeAction.checkPrecondition(player));

        player.upgradeRoom();
        assertEquals(FieldType.STONE,player.getRoomType());
    }

    @Test
    void runAction() {
        player.addResource(ResourceType.REED,4);
        player.addResource(ResourceType.CLAY,4);
        player.addResource(ResourceType.STONE,4);

        if(roomUpgradeAction.checkPrecondition(player))
            roomUpgradeAction.runAction(player);
        assertEquals(FieldType.CLAY,player.getRoomType());

        if(roomUpgradeAction.checkPrecondition(player))
            roomUpgradeAction.runAction(player);
        assertEquals(FieldType.STONE,player.getRoomType());

        roomUpgradeAction.runAction(player);
        assertEquals(FieldType.STONE,player.getRoomType());
    }
}