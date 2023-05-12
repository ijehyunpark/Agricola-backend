package com.semoss.agricola.GamePlay.domain.Action;

import com.semoss.agricola.GamePlay.domain.action.BasicAction;
import com.semoss.agricola.GamePlay.domain.player.Player;
import com.semoss.agricola.GamePlay.domain.resource.ResourceStruct;
import com.semoss.agricola.GamePlay.domain.resource.ResourceType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BasicActionTest {
    Player player;
    BasicAction basicAction;

    @BeforeEach
    void setUp() {
        player = Player.builder()
                .userId(1234L)
                .isStartPlayer(true)
                .build();
    }

    @Test
    void runAction() {
        assertEquals(0,player.getResource(ResourceType.WOOD));
        basicAction = BasicAction.builder()
                .resource(ResourceStruct.builder()
                        .resource(ResourceType.WOOD)
                        .count(3)
                        .build())
                .build();
        basicAction.runAction(player);
        assertEquals(3,player.getResource(ResourceType.WOOD));
        basicAction.runAction(player);
        assertEquals(6,player.getResource(ResourceType.WOOD));
    }
}