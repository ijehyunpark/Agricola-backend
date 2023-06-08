package com.semoss.agricola.GamePlay.domain.action.implement;

import com.semoss.agricola.GamePlay.domain.action.component.ActionFactory;
import com.semoss.agricola.GamePlay.domain.action.Event;
import com.semoss.agricola.GamePlay.domain.card.CardDictionary;
import com.semoss.agricola.GamePlay.domain.player.FieldType;
import com.semoss.agricola.GamePlay.domain.player.Player;
import com.semoss.agricola.GamePlay.domain.player.RoomType;
import com.semoss.agricola.GamePlay.domain.resource.ResourceType;
import com.semoss.agricola.GamePlay.dto.AgricolaActionRequest;
import com.semoss.agricola.GamePlay.dto.BuildActionExtensionRequest;
import com.semoss.agricola.GamePlay.exception.ResourceLackException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class Action1Test {
    Player player;
    Player startPlayer;
    Event event;
    CardDictionary cardDictionary;

    @BeforeEach
    void setUp(){
        startPlayer = mock(Player.class);
        ActionFactory actionFactory = new ActionFactory();
        cardDictionary = mock(CardDictionary.class);

        player = Player.builder()
                .userId(1234L)
                .isStartPlayer(true)
                .build();

        event = Event.builder()
                .action(new Action1(actionFactory.buildRoomAction(), actionFactory.buildStableAction()))
                .build();

    }

    @Test
    @DisplayName("Action1 테스트: 나무집 건설 성공")
    void test1(){
        // given
        player.addResource(ResourceType.WOOD, 5);
        player.addResource(ResourceType.REED, 2);
        List<AgricolaActionRequest.ActionFormat> acts = List.of(
                new AgricolaActionRequest.ActionFormat(true, new BuildActionExtensionRequest(2,4)),
                new AgricolaActionRequest.ActionFormat(false, null));

        // when
        event.runActions(player, startPlayer, 0,  acts, cardDictionary);

        // then
        assertEquals(0, player.getResource(ResourceType.WOOD));
        assertEquals(0, player.getResource(ResourceType.GRAIN));
        assertEquals(RoomType.WOOD, player.getRoomType());
        assertEquals(FieldType.ROOM, player.getPlayerBoard().getFields()[2][4].getFieldType());
    }

    @Test
    @DisplayName("Action1 테스트: 흙집 건설 성공")
    void test2(){
        // given
        player.addResource(ResourceType.CLAY, 5);
        player.addResource(ResourceType.REED, 2);
        player.upgradeRoom();
        List<AgricolaActionRequest.ActionFormat> acts = List.of(
                new AgricolaActionRequest.ActionFormat(true, new BuildActionExtensionRequest(2,4)),
                new AgricolaActionRequest.ActionFormat(false, null));

        // when
        event.runActions(player, startPlayer, 0,  acts, cardDictionary);

        // then
        assertEquals(0, player.getResource(ResourceType.CLAY));
        assertEquals(0, player.getResource(ResourceType.GRAIN));
        assertEquals(RoomType.CLAY, player.getRoomType());
        assertEquals(FieldType.ROOM, player.getPlayerBoard().getFields()[2][4].getFieldType());
    }

    @Test
    @DisplayName("Action1 테스트: 돌집 건설 성공")
    void test3(){
        // given
        player.addResource(ResourceType.STONE, 5);
        player.addResource(ResourceType.REED, 2);
        player.upgradeRoom();
        player.upgradeRoom();
        List<AgricolaActionRequest.ActionFormat> acts = List.of(
                new AgricolaActionRequest.ActionFormat(true, new BuildActionExtensionRequest(2,4)),
                new AgricolaActionRequest.ActionFormat(false, null));

        // when
        event.runActions(player, startPlayer, 0,  acts, cardDictionary);

        // then
        assertEquals(0, player.getResource(ResourceType.STONE));
        assertEquals(0, player.getResource(ResourceType.GRAIN));
        assertEquals(RoomType.STONE, player.getRoomType());
        assertEquals(FieldType.ROOM, player.getPlayerBoard().getFields()[2][4].getFieldType());
    }

    @Test
    @DisplayName("Action1 테스트: 자원 부족 집 건설 실패")
    void test4(){
        // given
        List<AgricolaActionRequest.ActionFormat> acts = List.of(
                new AgricolaActionRequest.ActionFormat(true, new BuildActionExtensionRequest(2,4)),
                new AgricolaActionRequest.ActionFormat(false, null));

        // when
        assertThrows(ResourceLackException.class, () -> {
            event.runActions(player, startPlayer, 0,  acts, cardDictionary);
        });

        // then
    }

    @Test
    @DisplayName("Action1 테스트: 집 건설 후 외양간 건설 성공")
    void test5(){
        // given
        player.addResource(ResourceType.WOOD, 7);
        player.addResource(ResourceType.REED, 2);
        List<AgricolaActionRequest.ActionFormat> acts = List.of(
                new AgricolaActionRequest.ActionFormat(true, new BuildActionExtensionRequest(2,4)),
                new AgricolaActionRequest.ActionFormat(true, new BuildActionExtensionRequest(2,3)));

        // when
        event.runActions(player, startPlayer, 0,  acts, cardDictionary);

        // then
        assertEquals(FieldType.ROOM, player.getPlayerBoard().getFields()[2][4].getFieldType());
        assertEquals(FieldType.STABLE, player.getPlayerBoard().getFields()[2][3].getFieldType());
    }

    @Test
    @DisplayName("Action1 테스트: 외양간만 건설 성공")
    void test6(){
        // given
        player.addResource(ResourceType.WOOD, 2);
        List<AgricolaActionRequest.ActionFormat> acts = List.of(
                new AgricolaActionRequest.ActionFormat(false, null),
                new AgricolaActionRequest.ActionFormat(true, new BuildActionExtensionRequest(2,4)));

        // when
        event.runActions(player, startPlayer, 0,  acts, cardDictionary);

        // then
        assertEquals(FieldType.STABLE, player.getPlayerBoard().getFields()[2][4].getFieldType());
    }



}