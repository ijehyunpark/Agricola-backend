package com.semoss.agricola.GamePlay.domain;

import com.semoss.agricola.GamePlay.domain.action.implement.DefaultAction;
import com.semoss.agricola.GamePlay.domain.card.CardDictionary;
import com.semoss.agricola.GameRoomCommunication.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

@SpringBootTest
class AgricolaGameTest {
    AgricolaGame game;
    @Autowired
    ObjectProvider<AgricolaGame> agricolaGameProvider;
    @Autowired
    ObjectProvider<DefaultAction> actionProvider;

    @Autowired
    ObjectProvider<CardDictionary> cardDictionaryProvider;



    @BeforeEach
    void setUp(){
    }

    @Test
    void createBeanTest() {
        List<User> users = List.of(User.builder().build(),
                User.builder().build());
        AgricolaGame game1 = agricolaGameProvider.getObject(users, "NONE",
                actionProvider.stream().toList(), cardDictionaryProvider.getObject());
        AgricolaGame game2 = agricolaGameProvider.getObject(users, "NONE",
                actionProvider.stream().toList(), cardDictionaryProvider.getObject());

        assertNotEquals(game1.hashCode(), game2.hashCode());

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