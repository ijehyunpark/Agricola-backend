package com.semoss.agricola.GamePlay.domain.action;

import com.semoss.agricola.GamePlay.domain.player.Farm;
import com.semoss.agricola.GamePlay.domain.player.Field;
import com.semoss.agricola.GamePlay.domain.player.FieldType;
import com.semoss.agricola.GamePlay.domain.player.Player;
import com.semoss.agricola.GamePlay.domain.resource.ResourceType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CultivationActionTest {
    Player player;
    CultivationAction action;
    @BeforeEach
    void setUp() {
        player = Player.builder()
                .userId(1234L)
                .isStartPlayer(true)
                .build();
        action = CultivationAction.builder().build();
    }

    @Test
    @DisplayName("runAction_씨앗 심기 테스트 : 성공")
    void runAction1() {
        //given
        player.buildField(2,3, FieldType.FARM);
        player.addResource(ResourceType.VEGETABLE, 1);

        Field field = player.getPlayerBoard().getFields()[2][3];
        assertEquals(FieldType.FARM, field.getFieldType());
        Farm farm = (Farm) field;

        //when
        action.runAction(player, 2, 3, ResourceType.VEGETABLE);

        //then
        assertEquals(ResourceType.VEGETABLE, farm.getSeed().getResource());
        assertEquals(3, farm.getSeed().getCount());
    }

    @Test
    @DisplayName("runAction_자원 부족으로 씨앗 심기 테스트 : 실패")
    void runAction2() {
        //given
        player.buildField(2,3, FieldType.FARM);

        Field field = player.getPlayerBoard().getFields()[2][3];
        assertEquals(FieldType.FARM, field.getFieldType());
        Farm farm = (Farm) field;

        //when
        assertThrows(RuntimeException.class, () ->{
            action.runAction(player, 2, 3, ResourceType.VEGETABLE);
        });

        //then
    }

    @Test
    @DisplayName("runAction_밭이 아니므로 씨앗 심기 테스트 : 실패")
    void runAction3() {
        //given
        player.addResource(ResourceType.VEGETABLE, 1);

        //when
        assertThrows(RuntimeException.class, () ->{
            action.runAction(player, 2, 3, ResourceType.VEGETABLE);
        });

        //then
    }

    @Test
    @DisplayName("runAction_부적절한 위치 씨앗 심기 테스트 : 실패")
    void runAction4() {
        //given
        player.addResource(ResourceType.VEGETABLE, 1);

        //when
        assertThrows(RuntimeException.class, () ->{
            action.runAction(player, 3, -1, ResourceType.VEGETABLE);
        });

        //then
    }


    @Test
    @DisplayName("runAction_이미 심어진 밭에 씨앗 심기 테스트 : 실패")
    void runAction5() {
        //given
        player.buildField(2,3, FieldType.FARM);
        player.addResource(ResourceType.VEGETABLE, 2);

        //when
        action.runAction(player, 2, 3, ResourceType.VEGETABLE);
        assertThrows(RuntimeException.class, () ->{
            action.runAction(player, 2, 3, ResourceType.VEGETABLE);
        });

        //then
    }
}