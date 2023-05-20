package com.semoss.agricola.GamePlay.domain.action;

import com.semoss.agricola.GamePlay.domain.player.Player;
import com.semoss.agricola.GamePlay.domain.resource.ResourceStruct;
import com.semoss.agricola.GamePlay.domain.resource.ResourceType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BasicActionTest {
    Player player;
    BasicAction action;

    @BeforeEach
    void setUp() {
        player = Player.builder()
                .userId(1234L)
                .isStartPlayer(true)
                .build();
    }

    @Test
    @DisplayName("runAction: 자원획득테스트")
    void runAction() {
        // given
        assertEquals(0,player.getResource(ResourceType.WOOD));
        action = BasicAction.builder()
                .resource(ResourceStruct.builder()
                        .resource(ResourceType.WOOD)
                        .count(3)
                        .build())
                .build();

        // when
        action.runAction(player);
        int result1 = player.getResource(ResourceType.WOOD);
        action.runAction(player);
        int result2 = player.getResource(ResourceType.WOOD);

        // then
        assertEquals(3,result1);
        assertEquals(6,result2);
    }
}