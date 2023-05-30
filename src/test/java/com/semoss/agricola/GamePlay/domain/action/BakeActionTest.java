package com.semoss.agricola.GamePlay.domain.action;

import com.semoss.agricola.GamePlay.domain.AgricolaGame;
import com.semoss.agricola.GamePlay.domain.card.CardDictionary;
import com.semoss.agricola.GamePlay.domain.card.MajorCard;
import com.semoss.agricola.GamePlay.domain.player.Player;
import com.semoss.agricola.GamePlay.domain.resource.ResourceType;
import com.semoss.agricola.GamePlay.exception.NotFoundException;
import com.semoss.agricola.GamePlay.exception.ResourceLackException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BakeActionTest {
    Player player;
    BakeAction bakeAction;
    MajorCard majorCard;
    AgricolaGame game;
    CardDictionary cardDictionary;

    @BeforeEach
    void setUp() {
        game = mock(AgricolaGame.class);
        player = Player.builder()
                .game(game)
                .userId(1234L)
                .isStartPlayer(true)
                .build();
        bakeAction = BakeAction.builder()
                .build();
        majorCard = MajorCard.builder()
                .cardID(1L)
                .bakeEfficiency(1)
                .owner(1234L)
                .build();
        cardDictionary = new CardDictionary();
    }

    @Test
    @DisplayName("빵 굽기 테스트 : 성공")
    void runAction() {
        // given
        when(game.getCardDictionary()).thenReturn(cardDictionary);
        player.addMajorCard(1L);
        player.addResource(ResourceType.GRAIN, 1);

        // when
        bakeAction.runAction(player, majorCard);

        // then
        assertEquals(1, player.getResource(ResourceType.FOOD));
    }

    @Test
    @DisplayName("자원 부족으로 빵 굽기 테스트 : 실패")
    void runAction2() {
        // given
        when(game.getCardDictionary()).thenReturn(cardDictionary);
        player.addMajorCard(1L);

        // when
        assertThrows(
                ResourceLackException.class, () -> {
                    bakeAction.runAction(player, majorCard);
                }
        );

        // then

    }

    @Test
    @DisplayName("주설비 없이 빵 굽기 테스트 : 실패")
    void runAction3() {
        // given
        player.addResource(ResourceType.GRAIN, 1);

        // when
        assertThrows(
                NotFoundException.class, () -> {
                    bakeAction.runAction(player, majorCard);
                }
        );

        // then

    }
}