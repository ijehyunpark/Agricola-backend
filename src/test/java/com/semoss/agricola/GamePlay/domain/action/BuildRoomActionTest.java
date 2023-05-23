package com.semoss.agricola.GamePlay.domain.action;

import com.semoss.agricola.GamePlay.domain.player.FieldType;
import com.semoss.agricola.GamePlay.domain.player.Player;
import com.semoss.agricola.GamePlay.domain.resource.ResourceType;
import com.semoss.agricola.GamePlay.exception.NotAllowRequestException;
import com.semoss.agricola.GamePlay.exception.ResourceLackException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class BuildRoomActionTest {
    Player player;
    BuildRoomAction action;

    @BeforeEach
    void setUp() {
        player = Player.builder().build();
        BakeAction bakeAction;
        ActionFactory actionFactory = new ActionFactory();
        action = actionFactory.buildRoomAction();
    }

    @Test
    @DisplayName("집 건설 : 성공")
    void test1() {
        // given
        player.addResource(ResourceType.WOOD, 5);
        player.addResource(ResourceType.REED, 2);

        // when
        action.runAction(player, 2, 2);

        // then
        assertEquals(FieldType.ROOM, player.getPlayerBoard().getFields()[2][2].getFieldType());
        assertEquals(0, player.getResource(ResourceType.WOOD));
        assertEquals(0, player.getResource(ResourceType.REED));
    }

    @Test
    @DisplayName("부적절한 위치에 집 건설 : 실패")
    void test2() {
        // given
        player.addResource(ResourceType.WOOD, 5);
        player.addResource(ResourceType.REED, 2);

        // when

        assertThrows(
                NotAllowRequestException.class,
                () -> {
                    action.runAction(player, 3, 2);
                }
        );

        // then
    }

    @Test
    @DisplayName("자원 부족으로 집 건설 : 실패")
    void test4() {
        // given
        player.addResource(ResourceType.WOOD, 5);

        // when

        assertThrows(
                ResourceLackException.class,
                () -> {
                    action.runAction(player, 3, 2);
                }
        );

        // then
    }

    @Test
    @DisplayName("업그레이드를 반영한 집 건설 : 성공")
    void test3() {
        // given
        player.addResource(ResourceType.STONE, 5);
        player.addResource(ResourceType.WOOD, 5);
        player.addResource(ResourceType.CLAY, 5);
        player.addResource(ResourceType.REED, 6);


        // when
        action.runAction(player, 2, 2);
        player.upgradeRoom();
        action.runAction(player, 2, 3);
        player.upgradeRoom();
        action.runAction(player, 2, 4);

        // then
        assertEquals(FieldType.ROOM, player.getPlayerBoard().getFields()[2][2].getFieldType());
        assertEquals(FieldType.ROOM, player.getPlayerBoard().getFields()[2][3].getFieldType());
        assertEquals(FieldType.ROOM, player.getPlayerBoard().getFields()[2][4].getFieldType());
        assertEquals(0, player.getResource(ResourceType.WOOD));
        assertEquals(0, player.getResource(ResourceType.CLAY));
        assertEquals(0, player.getResource(ResourceType.REED));
        assertEquals(0, player.getResource(ResourceType.STONE));
    }
}