package com.semoss.agricola.GamePlay.domain.Action;

import com.semoss.agricola.GamePlay.domain.action.RoomUpgradeAction;
import com.semoss.agricola.GamePlay.domain.player.FieldType;
import com.semoss.agricola.GamePlay.domain.player.Player;
import com.semoss.agricola.GamePlay.domain.player.RoomType;
import com.semoss.agricola.GamePlay.domain.resource.ResourceStruct;
import com.semoss.agricola.GamePlay.domain.resource.ResourceType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class RoomUpgradeActionTest {
    Player player;
    RoomUpgradeAction roomUpgradeAction;

    RoomUpgradeAction buildRoomUpgradeAction() {
        Map<ResourceType, List<ResourceStruct>> costs = new HashMap<>();
        // 나무집 -> 흙집 업그레이드 비용
        costs.put(ResourceType.CLAY, new ArrayList<>(Arrays.asList(
                ResourceStruct.builder()
                        .resource(ResourceType.REED)
                        .count(1)
                        .build(), ResourceStruct.builder()
                        .resource(ResourceType.CLAY)
                        .count(1)
                        .build())));

        // 흙집 -> 돌집 업그레이드 비용
        costs.put(ResourceType.STONE, new ArrayList<>(Arrays.asList(
                ResourceStruct.builder()
                        .resource(ResourceType.REED)
                        .count(1)
                        .build(), ResourceStruct.builder()
                        .resource(ResourceType.STONE)
                        .count(1)
                        .build())));

        return RoomUpgradeAction.builder()
                .costs(costs)
                .build();
    }
    @BeforeEach
    void setUp(){
        player = Player.builder()
                .userId(1234L)
                .isStartPlayer(true)
                .build();

        roomUpgradeAction = buildRoomUpgradeAction();
    }

    @Test
    void checkPrecondition() {
        player.upgradeRoom();
        assertEquals(RoomType.CLAY,player.getRoomType());
        assertFalse(roomUpgradeAction.checkPrecondition(player));

        player.addResource(ResourceType.REED,4);
        player.addResource(ResourceType.STONE,4);
        assertTrue(roomUpgradeAction.checkPrecondition(player));

        player.upgradeRoom();
        assertEquals(RoomType.STONE,player.getRoomType());
        assertFalse(roomUpgradeAction.checkPrecondition(player));

        player.upgradeRoom();
        assertEquals(RoomType.STONE,player.getRoomType());
    }

    @Test
    void runAction() {
        player.addResource(ResourceType.REED,4);
        player.addResource(ResourceType.CLAY,4);
        player.addResource(ResourceType.STONE,4);

        if(roomUpgradeAction.checkPrecondition(player))
            roomUpgradeAction.runAction(player);
        assertEquals(RoomType.CLAY,player.getRoomType());

        if(roomUpgradeAction.checkPrecondition(player))
            roomUpgradeAction.runAction(player);
        assertEquals(RoomType.STONE,player.getRoomType());

        roomUpgradeAction.runAction(player);
        assertEquals(RoomType.STONE,player.getRoomType());
    }
}