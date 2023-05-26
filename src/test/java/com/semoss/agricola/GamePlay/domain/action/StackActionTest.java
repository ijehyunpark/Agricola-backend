package com.semoss.agricola.GamePlay.domain.action;

import com.semoss.agricola.GamePlay.domain.player.Player;
import com.semoss.agricola.GamePlay.domain.resource.ResourceType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Disabled
class StackActionTest {
    Player player;
    StackResourceAction stackAction;

    @BeforeEach
    void setUp() {
        player = Player.builder()
                .userId(1234L)
                .isStartPlayer(true)
                .build();
        stackAction = new StackResourceAction(ResourceType.WOOD,3);
    }

    @Test
    void testStackResource() {
        // given

        // when

        // then
        assertEquals(ResourceType.WOOD,stackAction.getStackResource().getResource());
        assertEquals(3,stackAction.getStackResource().getCount());
    }
}