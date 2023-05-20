package com.semoss.agricola.GamePlay.domain;

import com.semoss.agricola.GameRoomCommunication.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AgricolaGameTest {
    AgricolaGame game;

    @BeforeEach
    void setUp(){
        game = AgricolaGame.builder()
                .users(List.of(
                        User.builder().build(),
                        User.builder().build(),
                        User.builder().build(),
                        User.builder().build()))
                .build();
    }
    @Test
    void findNextPlayer() {
    }

    @Test
    void findNextActionPlayer() {
    }

    @Test
    void initPlayerPlayed() {
    }

    @Test
    void growUpChild() {
    }

    @Test
    void playAction() {
    }

    @Test
    void playExchange() {
    }
}