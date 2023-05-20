package com.semoss.agricola.GamePlay.domain.action;

import com.semoss.agricola.GamePlay.domain.player.Player;
import com.semoss.agricola.GamePlay.domain.player.RoomType;
import com.semoss.agricola.GamePlay.domain.resource.ResourceStruct;
import com.semoss.agricola.GamePlay.domain.resource.ResourceType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
    @DisplayName("runAction: 방 업그레이드 성공")
    void runAction1() {
        // given
        player.addResource(ResourceType.REED,4);
        player.addResource(ResourceType.CLAY,4);
        player.addResource(ResourceType.STONE,4);

        // when
        roomUpgradeAction.runAction(player);
        RoomType result1 = player.getRoomType();
        roomUpgradeAction.runAction(player);
        RoomType result2 = player.getRoomType();

        // then
        assertEquals(RoomType.CLAY, result1);
        assertEquals(RoomType.STONE, result2);
    }

    @Test
    @DisplayName("runAction: 비용 부족으로 방 업그레이드 실패")
    void runAction2() {
        // given

        // when
        assertThrows(RuntimeException.class, () -> roomUpgradeAction.runAction(player));

        // then
    }
}