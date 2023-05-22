package com.semoss.agricola.GamePlay.domain.action;

import com.semoss.agricola.GamePlay.domain.player.Farm;
import com.semoss.agricola.GamePlay.domain.player.Field;
import com.semoss.agricola.GamePlay.domain.player.FieldType;
import com.semoss.agricola.GamePlay.domain.player.Player;
import com.semoss.agricola.GamePlay.domain.resource.ResourceType;
import com.semoss.agricola.GamePlay.dto.CultivationActionExtentionRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

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
        List<CultivationActionExtentionRequest> request = List.of(new CultivationActionExtentionRequest(2, 3, ResourceType.VEGETABLE));

        //when
        action.runAction(player, request);

        //then
        assertEquals(ResourceType.VEGETABLE, farm.getSeed().getResource());
        assertEquals(3, farm.getSeed().getCount());
    }

    @Test
    @DisplayName("runAction_씨앗 심기 테스트 다중 요청 : 성공")
    void runAction7() {
        //given
        player.buildField(2,3, FieldType.FARM);
        player.buildField(2,4, FieldType.FARM);
        player.addResource(ResourceType.GRAIN, 1);
        player.addResource(ResourceType.VEGETABLE, 1);

        Field field = player.getPlayerBoard().getFields()[2][3];
        assertEquals(FieldType.FARM, field.getFieldType());
        Farm farm = (Farm) field;
        List<CultivationActionExtentionRequest> request = List.of(new CultivationActionExtentionRequest(2, 3, ResourceType.VEGETABLE),
                new CultivationActionExtentionRequest(2, 4, ResourceType.GRAIN));

        //when
        action.runAction(player, request);

        //then
        assertEquals(ResourceType.VEGETABLE, farm.getSeed().getResource());
        assertEquals(3, farm.getSeed().getCount());
        assertEquals(0, player.getResource(ResourceType.VEGETABLE));
        assertEquals(0, player.getResource(ResourceType.GRAIN));
    }

    @Test
    @DisplayName("runAction_자원 부족으로 씨앗 심기 테스트 : 실패")
    void runAction2() {
        //given
        player.buildField(2,3, FieldType.FARM);

        Field field = player.getPlayerBoard().getFields()[2][3];
        assertEquals(FieldType.FARM, field.getFieldType());
        Farm farm = (Farm) field;
        List<CultivationActionExtentionRequest> request = List.of(new CultivationActionExtentionRequest(2, 3, ResourceType.VEGETABLE));

        //when
        assertThrows(RuntimeException.class, () ->{
            action.runAction(player, request);
        });

        //then
    }

    @Test
    @DisplayName("runAction_밭이 아니므로 씨앗 심기 테스트 : 실패")
    void runAction3() {
        //given
        player.addResource(ResourceType.VEGETABLE, 1);
        List<CultivationActionExtentionRequest> request = List.of(new CultivationActionExtentionRequest(2, 3, ResourceType.VEGETABLE));

        //when
        assertThrows(RuntimeException.class, () ->{
            action.runAction(player, request);
        });

        //then
    }

    @Test
    @DisplayName("runAction_부적절한 위치 씨앗 심기 테스트 : 실패")
    void runAction4() {
        //given
        player.addResource(ResourceType.VEGETABLE, 1);
        List<CultivationActionExtentionRequest> request = List.of(new CultivationActionExtentionRequest(3, -1, ResourceType.VEGETABLE));

        //when
        assertThrows(RuntimeException.class, () ->{
            action.runAction(player, request);
        });

        //then
    }


    @Test
    @DisplayName("runAction_이미 심어진 밭에 씨앗 심기 테스트 : 실패")
    void runAction5() {
        //given
        player.buildField(2,3, FieldType.FARM);
        player.addResource(ResourceType.VEGETABLE, 2);
        List<CultivationActionExtentionRequest> request = List.of(new CultivationActionExtentionRequest(2, 3, ResourceType.VEGETABLE));

        //when
        action.runAction(player, request);
        assertThrows(RuntimeException.class, () ->{
            action.runAction(player, request);
        });

        //then
    }

    @Test
    @DisplayName("runAction_중복된 요청이 있는 경우 씨앗 심기 테스트 : 실패")
    void runAction6() {
        //given
        player.buildField(2,3, FieldType.FARM);
        player.addResource(ResourceType.VEGETABLE, 2);
        List<CultivationActionExtentionRequest> request = List.of(new CultivationActionExtentionRequest(2, 3, ResourceType.VEGETABLE),
                new CultivationActionExtentionRequest(2, 3, ResourceType.VEGETABLE));

        //when
        assertThrows(RuntimeException.class, () ->{
            action.runAction(player, request);
        });

        //then
    }
}