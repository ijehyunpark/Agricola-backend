package com.semoss.agricola.GamePlay.domain.action.component;

import com.semoss.agricola.GamePlay.domain.History;
import com.semoss.agricola.GamePlay.domain.player.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class GetStartingPositionActionTest {
    Player targetplayer;
    Player startPlayer;
    GetStartingPositionAction action;
    History history;

    @BeforeEach
    void setUp() {
        action = GetStartingPositionAction.builder().build();
        startPlayer = Player.builder()
                .isStartPlayer(true)
                .build();

        targetplayer = Player.builder()
                .isStartPlayer(false)
                .build();

        history = History.builder().build();
    }

    @Test
    @DisplayName("runAction_선공권 회수 테스트")
    void runAction_1() {
        //given

        //when
        action.runAction(targetplayer, startPlayer, history);

        //then
        assertTrue(targetplayer.isStartingToken());
        assertFalse(startPlayer.isStartingToken());
    }

    @Test
    @DisplayName("runAction_선공 플레이어의 선공권 회수 테스트")
    void runAction_2() {
        // given

        // when
        action.runAction(startPlayer,startPlayer, history);

        // then
        assertFalse(targetplayer.isStartingToken());
        assertTrue(startPlayer.isStartingToken());
    }
}