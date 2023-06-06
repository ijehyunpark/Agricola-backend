package com.semoss.agricola.GamePlay.domain.action.component;

import com.semoss.agricola.GamePlay.domain.action.Event;
import com.semoss.agricola.GamePlay.domain.action.implement.Action10;
import com.semoss.agricola.GamePlay.domain.player.Player;
import com.semoss.agricola.GamePlay.domain.resource.ResourceStruct;
import com.semoss.agricola.GamePlay.domain.resource.ResourceType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class StackActionTest {
    Player player;
    Event event;
    StackResourceAction stackAction;

    @BeforeEach
    void setUp() {
        player = Player.builder().build();


        stackAction = StackResourceAction.builder()
                .resource(ResourceStruct.builder()
                        .resource(ResourceType.WOOD)
                        .count(3)
                        .build())
                .build();

        Action10 action10 = new Action10(stackAction);

        event = Event.builder()
                .action(action10)
                .build();
    }

    @Test
    void testStackResource() {
        // given

        // when
        event.processStackEvent();

        // then
        assertEquals(ResourceType.WOOD, ((ResourceStruct) event.getStacks().get(0)).getResource());
        assertEquals(3, ((ResourceStruct) event.getStacks().get(0)).getCount());
    }
}