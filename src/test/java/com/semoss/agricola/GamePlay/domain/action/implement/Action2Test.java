package com.semoss.agricola.GamePlay.domain.action.implement;

import com.semoss.agricola.GamePlay.domain.AgricolaGame;
import com.semoss.agricola.GamePlay.domain.action.ActionFactory;
import com.semoss.agricola.GamePlay.domain.action.Event;
import com.semoss.agricola.GamePlay.domain.card.CardDictionary;
import com.semoss.agricola.GamePlay.domain.player.Player;
import com.semoss.agricola.GamePlay.dto.AgricolaActionRequest;
import com.semoss.agricola.GamePlay.dto.PlaceExtentionRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


class Action2Test {

    AgricolaGame game;
    Player startPlayer;
    Player secondPlayer;
    Event event;
    CardDictionary cardDictionary;

    @BeforeEach
    void setUp(){
        ActionFactory actionFactory = new ActionFactory();
        game = mock(AgricolaGame.class);
        cardDictionary = mock(CardDictionary.class);

        startPlayer = Player.builder()
                .game(game)
                .userId(2345L)
                .isStartPlayer(true)
                .build();

        secondPlayer = Player.builder()
                .game(game)
                .userId(1234L)
                .isStartPlayer(false)
                .build();

        event = Event.builder()
                .action(new Action2(actionFactory.getStartingPositionAction(), actionFactory.placeMinorCardAction()))
                .build();

    }

    @Test
    @DisplayName("Action2 테스트: 시작 플레이어 되기")
    void test1(){
        // given
        List<AgricolaActionRequest.ActionFormat> acts = List.of(
                new AgricolaActionRequest.ActionFormat(true, null),
                new AgricolaActionRequest.ActionFormat(false, null));
        when(game.getPlayers()).thenReturn(List.of(startPlayer, secondPlayer));

        // when
        event.runActions(secondPlayer, acts, cardDictionary);

        // then
        assertTrue(secondPlayer.isStartingToken());
        assertFalse(startPlayer.isStartingToken());
    }


    @Test
    @DisplayName("Action2 테스트: 이미 시작 플레이어 된 경우")
    void test2(){
        // given
        List<AgricolaActionRequest.ActionFormat> acts = List.of(
                new AgricolaActionRequest.ActionFormat(true, null),
                new AgricolaActionRequest.ActionFormat(false, null));
        when(game.getPlayers()).thenReturn(List.of(startPlayer, secondPlayer));

        // when
        event.runActions(startPlayer, acts, cardDictionary);

        // then
        assertTrue(startPlayer.isStartingToken());
        assertFalse(secondPlayer.isStartingToken());
    }

    @Test
    @DisplayName("Action2 테스트: 보조 설비 테스트")
    void test3(){
        // given
        List<AgricolaActionRequest.ActionFormat> acts = List.of(
                new AgricolaActionRequest.ActionFormat(true, null),
                new AgricolaActionRequest.ActionFormat(true, new PlaceExtentionRequest(100L)));
        when(game.getPlayers()).thenReturn(List.of(startPlayer, secondPlayer));

        // when

        // then
        assertTrue(false);
    }
}