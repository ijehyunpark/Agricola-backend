package com.semoss.agricola.GamePlay.domain.action.component;

import com.semoss.agricola.GamePlay.domain.History;
import com.semoss.agricola.GamePlay.domain.player.Player;
import com.semoss.agricola.GamePlay.domain.player.RoomType;
import com.semoss.agricola.GamePlay.domain.resource.ResourceType;
import com.semoss.agricola.GamePlay.exception.IllegalRequestException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class RoomUpgradeActionTest {
    Player player;
    RoomUpgradeAction action;
    History history;

    @BeforeEach
    void setUp(){
        player = Player.builder().build();

        ActionFactory actionFactory = new ActionFactory();
        action = actionFactory.roomUpgradeAction();
        history = History.builder().build();
    }

    @Test
    @DisplayName("runAction: 방 업그레이드 성공")
    void runAction1() {
        // given
        player.addResource(ResourceType.REED,4);
        player.addResource(ResourceType.CLAY,2);
        player.addResource(ResourceType.STONE,2);

        // when
        action.runAction(player, history);
        RoomType result1 = player.getRoomType();
        action.runAction(player, history);
        RoomType result2 = player.getRoomType();

        // then
        assertEquals(RoomType.CLAY, result1);
        assertEquals(RoomType.STONE, result2);
        assertEquals(0, player.getResource(ResourceType.REED));
        assertEquals(0, player.getResource(ResourceType.CLAY));
        assertEquals(0, player.getResource(ResourceType.STONE));
    }

    @Test
    @DisplayName("runAction: 비용 부족으로 방 업그레이드 실패")
    void runAction2() {
        // given

        // when
        assertThrows(RuntimeException.class, () -> action.runAction(player, history));

        // then
    }

    @Test
    @DisplayName("runAction: 불가능한 방 업그레이드 실패")
    void runAction3() {
        // given
        player.addResource(ResourceType.REED,4);
        player.addResource(ResourceType.CLAY,4);
        player.addResource(ResourceType.STONE,4);

        // when
        action.runAction(player, history);
        action.runAction(player, history);

        assertThrows(IllegalRequestException.class, () -> action.runAction(player, history));

        // then
    }
}