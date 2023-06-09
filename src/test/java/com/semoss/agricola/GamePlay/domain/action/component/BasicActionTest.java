package com.semoss.agricola.GamePlay.domain.action.component;

import com.semoss.agricola.GamePlay.domain.History;
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
    History history;

    @BeforeEach
    void setUp() {
        player = Player.builder().build();
        history = History.builder().build();
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
        action.runAction(player, history);
        int result1 = player.getResource(ResourceType.WOOD);
        action.runAction(player, history);
        int result2 = player.getResource(ResourceType.WOOD);

        // then
        assertEquals(3,result1);
        assertEquals(6,result2);
    }

    @Test
    @DisplayName("runAction: 다중 자원획득테스트")
    void runAction2() {
        // given
        assertEquals(0,player.getResource(ResourceType.WOOD));
        action = BasicAction.builder()
                .resource(ResourceStruct.builder()
                        .resource(ResourceType.WOOD)
                        .count(3)
                        .build())
                .resource(ResourceStruct.builder()
                        .resource(ResourceType.REED)
                        .count(5)
                        .build())
                .build();

        // when
        action.runAction(player, history);

        // then
        assertEquals(3, player.getResource(ResourceType.WOOD));
        assertEquals(5, player.getResource(ResourceType.REED));
    }
}