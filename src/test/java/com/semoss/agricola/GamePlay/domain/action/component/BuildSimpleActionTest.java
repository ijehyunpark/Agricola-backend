package com.semoss.agricola.GamePlay.domain.action.component;

import com.semoss.agricola.GamePlay.domain.player.FieldType;
import com.semoss.agricola.GamePlay.domain.player.Player;
import com.semoss.agricola.GamePlay.domain.resource.ResourceType;
import com.semoss.agricola.GamePlay.exception.AlreadyExistException;
import com.semoss.agricola.GamePlay.exception.NotAllowRequestException;
import com.semoss.agricola.GamePlay.exception.ResourceLackException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class BuildSimpleActionTest {

    Player player;
    BuildSimpleAction buildFarmAction;
    BuildSimpleAction buildStableAction;

    @BeforeEach
    void setUp() {
        player = Player.builder().build();
        ActionFactory actionFactory = new ActionFactory();
        buildFarmAction = actionFactory.buildFarmAction();
        buildStableAction = actionFactory.buildStableAction();
    }

    @Test
    @DisplayName("농지 건설 : 성공")
    void test1() {
        // given

        // when
        buildFarmAction.runAction(player, 2, 3);

        // then
        assertEquals(FieldType.FARM, player.getPlayerBoard().getFields()[2][3].getFieldType());
    }

    @Test
    @DisplayName("건설된 곳 중복 건설 : 실패")
    void test2() {
        // given

        // when
        buildFarmAction.runAction(player, 2, 3);
        assertThrows(
                AlreadyExistException.class,
                () -> {

                    buildFarmAction.runAction(player, 2, 3);
                }
        );

        // then
    }

    @Test
    @DisplayName("경계값 테스트 : 실패")
    void test3() {
        // given

        // when
        assertThrows(
                NotAllowRequestException.class,
                () -> {
                    buildFarmAction.runAction(player, 3, 3);
                }
        );
        assertThrows(
                NotAllowRequestException.class,
                () -> {
                    buildFarmAction.runAction(player, -1, 2);
                }
        );
        assertThrows(
                NotAllowRequestException.class,
                () -> {
                    buildFarmAction.runAction(player, 2, 5);
                }
        );
        assertThrows(
                NotAllowRequestException.class,
                () -> {
                    buildFarmAction.runAction(player, 3, -1);
                }
        );

        // then
    }

    @Test
    @DisplayName("외양간 건설 : 성공")
    void test4() {
        // given
        player.addResource(ResourceType.WOOD, 2);

        // when
        buildStableAction.runAction(player, 2, 3);

        // then
        assertEquals(FieldType.STABLE, player.getPlayerBoard().getFields()[2][3].getFieldType());
        assertEquals(0, player.getResource(ResourceType.WOOD));
    }

    @Test
    @DisplayName("외양간 건설 자원 부족 : 실패")
    void test5() {
        // given

        // when
        assertThrows(
                ResourceLackException.class,
                () -> {
                    buildStableAction.runAction(player, 2, 3);
                }
        );

        // then
    }
}