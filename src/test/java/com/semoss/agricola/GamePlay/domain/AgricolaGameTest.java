package com.semoss.agricola.GamePlay.domain;

import com.semoss.agricola.GamePlay.domain.action.implement.DefaultAction;
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
    ObjectProvider<ImprovementBoard> improvementBoardProvider;



    @BeforeEach
    void setUp(){
    }

    @Test
    void createBeanTest() {
        List<User> users = List.of(User.builder().build(),
                User.builder().build());
        ImprovementBoard improvementBoard1 = improvementBoardProvider.getObject();

        ImprovementBoard improvementBoard2 = improvementBoardProvider.getObject();
        AgricolaGame game1 = agricolaGameProvider.getObject(users, "NONE",
                actionProvider.stream().toList(), improvementBoard1);
        AgricolaGame game2 = agricolaGameProvider.getObject(users, "NONE",
                actionProvider.stream().toList(), improvementBoard2);

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