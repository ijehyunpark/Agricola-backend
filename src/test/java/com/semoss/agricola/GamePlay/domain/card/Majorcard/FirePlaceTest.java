package com.semoss.agricola.GamePlay.domain.card.Majorcard;

import com.semoss.agricola.GamePlay.domain.AgricolaGame;
import com.semoss.agricola.GamePlay.domain.card.CardDictionary;
import com.semoss.agricola.GamePlay.domain.player.Player;
import com.semoss.agricola.GamePlay.domain.resource.ResourceStruct;
import com.semoss.agricola.GamePlay.domain.resource.ResourceType;
import com.semoss.agricola.GamePlay.exception.IllgalRequestException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FirePlaceTest {
    Player player;
    CardDictionary cardDictionary;
    MajorFactory majorFactory;
    AgricolaGame mockedGame;

    @BeforeEach
    void setUp(){
        majorFactory = new MajorFactory();
        cardDictionary = new CardDictionary();
        mockedGame = mock(AgricolaGame.class);

        cardDictionary.addCard(null,majorFactory.firePlace1());
        cardDictionary.addCard(null,majorFactory.firePlace2());
        cardDictionary.addCard(null,majorFactory.firePlace3());
        cardDictionary.addCard(null,majorFactory.firePlace4());
    }

    @Test
    @DisplayName("FirePlaceTest : 화로/화덕 획득 조건 확인")
    void test1(){
        //given
        player = Player.builder()
                .userId(1234L)
                .isStartPlayer(true)
                .game(mockedGame)
                .build();

        player.addResource(ResourceStruct.builder().resource(ResourceType.CLAY).count(4).build());
        boolean expected1 = true;
        boolean expected2 = true;
        boolean expected3 = true; // 경계값
        boolean expected4 = false;

        //when
        boolean actual1 = cardDictionary.getCard(1L).checkPrerequisites(player);
        boolean actual2 = cardDictionary.getCard(2L).checkPrerequisites(player);
        boolean actual3 = cardDictionary.getCard(3L).checkPrerequisites(player);
        boolean actual4 = cardDictionary.getCard(4L).checkPrerequisites(player);

        //then
        assertEquals(expected1,actual1);
        assertEquals(expected2,actual2);
        assertEquals(expected3,actual3);
        assertEquals(expected4,actual4);
    }

    @Test
    @DisplayName("FirePlaceTest : 화로/화덕 획득")
    void test2(){
        //given
        when(mockedGame.getCardDictionary()).thenReturn(cardDictionary);
        player = Player.builder()
                .userId(1234L)
                .isStartPlayer(true)
                .game(mockedGame)
                .build();
        player.addResource(ResourceStruct.builder().resource(ResourceType.CLAY).count(6).build());
        Player expected1 = player;
        Player expected2 = null;
        Player expected3 = player; // 경계값

        //when
        cardDictionary.getCard(1L).place(player);
        cardDictionary.getCard(3L).place(player);
        Player actual1 = cardDictionary.getOwner(1L);
        Player actual2 = cardDictionary.getOwner(2L);
        Player actual3 = cardDictionary.getOwner(3L);


        //then
        assertEquals(expected1,actual1);
        assertEquals(expected2,actual2);
        assertEquals(expected3,actual3);
        assertThrows(IllgalRequestException.class, () -> {
            cardDictionary.getCard(4L).place(player);
        });
    }
}