package com.semoss.agricola.GamePlay.domain.action;

import com.semoss.agricola.GamePlay.domain.AgricolaGame;
import com.semoss.agricola.GamePlay.domain.History;
import com.semoss.agricola.GamePlay.domain.player.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetStartingPositionActionTest {
    @Mock
    AgricolaGame game;
    @InjectMocks
    Player targetplayer;
    @InjectMocks
    Player startPlayer;
    GetStartingPositionAction action;
    History history;

    @BeforeEach
    void setUp() {
        action = GetStartingPositionAction.builder().build();
        startPlayer = Player.builder()
                .game(game)
                .userId(2345L)
                .isStartPlayer(true)
                .build();

        targetplayer = Player.builder()
                .game(game)
                .userId(1234L)
                .isStartPlayer(false)
                .build();

        history = History.builder().build();
    }

    @Test
    @DisplayName("runAction_선공권 회수 테스트")
    void runAction_1() {
        //given
        when(game.getPlayers()).thenReturn(new ArrayList<>(List.of(startPlayer, targetplayer)));

        //when
        action.runAction(targetplayer, history);

        //then
        assertTrue(targetplayer.isStartingToken());
        assertFalse(startPlayer.isStartingToken());
    }

    @Test
    @DisplayName("runAction_선공 플레이어의 선공권 회수 테스트")
    void runAction_2() {
        // given
        when(game.getPlayers()).thenReturn(new ArrayList<>(List.of(startPlayer, targetplayer)));

        // when
        action.runAction(startPlayer, history);

        // then
        assertFalse(targetplayer.isStartingToken());
        assertTrue(startPlayer.isStartingToken());
    }
}