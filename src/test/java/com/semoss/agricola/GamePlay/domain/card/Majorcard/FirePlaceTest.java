package com.semoss.agricola.GamePlay.domain.card.Majorcard;

import com.semoss.agricola.GamePlay.domain.AgricolaGame;
import com.semoss.agricola.GamePlay.domain.card.Card;
import com.semoss.agricola.GamePlay.domain.card.CardDictionary;
import com.semoss.agricola.GamePlay.domain.card.Minorcard.DummyMinorCard;
import com.semoss.agricola.GamePlay.domain.card.Occupation.DummyOccupation;
import com.semoss.agricola.GamePlay.domain.player.Player;
import com.semoss.agricola.GamePlay.domain.resource.ResourceStruct;
import com.semoss.agricola.GamePlay.domain.resource.ResourceType;
import com.semoss.agricola.GamePlay.exception.IllegalRequestException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.ObjectProvider;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class FirePlaceTest {
    Player player;
    CardDictionary cardDictionary;
    AgricolaGame mockedGame;

    FirePlace firePlace1;
    FirePlace firePlace2;
    FirePlace firePlace3;
    FirePlace firePlace4;


    @BeforeEach
    void setUp(){
        MajorFactory majorFactory = new MajorFactory();
        firePlace1 = majorFactory.firePlace1();
        firePlace2 = majorFactory.firePlace2();
        firePlace3 = majorFactory.firePlace3();
        firePlace4 = majorFactory.firePlace4();

        List<MajorCard> majorCardList = new ArrayList<>();
        majorCardList.add(firePlace1);
        majorCardList.add(firePlace2);
        majorCardList.add(firePlace3);
        majorCardList.add(firePlace4);

        ObjectProvider<DummyMinorCard> dummyMinorCards = mock(ObjectProvider.class);
        ObjectProvider<DummyOccupation> dummyOccupations = mock(ObjectProvider.class);
        cardDictionary = new CardDictionary(majorCardList, new ArrayList<>(), dummyMinorCards, new ArrayList<>(), dummyOccupations);

        mockedGame = mock(AgricolaGame.class);
    }

    @Test
    @DisplayName("FirePlaceTest : 화로/화덕 획득 조건 확인")
    void test1(){
        //given
        player = Player.builder().build();

        player.addResource(ResourceStruct.builder().resource(ResourceType.CLAY).count(4).build());
        boolean expected1 = true;
        boolean expected2 = true;
        boolean expected3 = true; // 경계값
        boolean expected4 = false;

        //when
        boolean actual1 = cardDictionary.getCard(1L).checkPrerequisites(player, cardDictionary);
        boolean actual2 = cardDictionary.getCard(2L).checkPrerequisites(player, cardDictionary);
        boolean actual3 = cardDictionary.getCard(3L).checkPrerequisites(player, cardDictionary);
        boolean actual4 = cardDictionary.getCard(4L).checkPrerequisites(player, cardDictionary);

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
        player = Player.builder().build();
        player.addResource(ResourceStruct.builder().resource(ResourceType.CLAY).count(6).build());
        Player expected1 = player;
        Player expected2 = null;
        Player expected3 = player; // 경계값

        //when
        firePlace1.place(player,cardDictionary, 0);
        firePlace3.place(player,cardDictionary, 0);
        Player actual1 = cardDictionary.getOwner(firePlace1).orElse(null);
        Player actual2 = cardDictionary.getOwner(firePlace2).orElse(null);;
        Player actual3 = cardDictionary.getOwner(firePlace3).orElse(null);;
        Card card = cardDictionary.getCard(4L);


        //then
        assertEquals(expected1,actual1);
        assertEquals(expected2,actual2);
        assertEquals(expected3,actual3);
        assertThrows(IllegalRequestException.class, () -> {
            card.place(player, cardDictionary, 0);
        });
    }
}