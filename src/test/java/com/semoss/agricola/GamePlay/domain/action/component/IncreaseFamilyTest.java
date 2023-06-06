package com.semoss.agricola.GamePlay.domain.action.component;

import com.semoss.agricola.GamePlay.domain.History;
import com.semoss.agricola.GamePlay.domain.player.FieldType;
import com.semoss.agricola.GamePlay.domain.player.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IncreaseFamilyTest {
    Player player;
    IncreaseFamilyAction increaseFamilyAction;
    History history;

    @BeforeEach
    void setUp() {
        player = Player.builder().build();
        history = History.builder().build();
    }
    @Test
    @DisplayName("runAction: 빈 방이 있는 경우 가족 늘리기 테스트 - 성공")
    void runActionPrecondition2() {
        // given
        increaseFamilyAction = IncreaseFamilyAction.builder()
                .precondition(true)
                .build();

        player.buildField(0, 0, FieldType.ROOM);
        assertTrue(player.existEmptyRoom());

        // when
        increaseFamilyAction.runAction(player, history);

        // then
        assertFalse(player.existEmptyRoom());
        assertEquals(3,player.getFamilyNumber());
    }

    @Test
    @DisplayName("runAction: 빈 방이 있는 경우 가족 늘리기 테스트 - 실패")
    void runActionPrecondition1() {
        // given
        increaseFamilyAction = IncreaseFamilyAction.builder()
                .precondition(true)
                .build();

        assertFalse(player.existEmptyRoom());

        // when
        assertThrows(RuntimeException.class, () -> increaseFamilyAction.runAction(player, history));

        // then
        assertFalse(player.existEmptyRoom());
        assertEquals(2,player.getFamilyNumber());

    }

    @Test
    @DisplayName("runAction: 급한 가족 늘리기 테스트")
    void runActionNotPrecondition2() {
        // given
        increaseFamilyAction = IncreaseFamilyAction.builder()
                .precondition(false)
                .build();

        assertFalse(player.existEmptyRoom());

        // when
        increaseFamilyAction.runAction(player, history);

        // then
        assertFalse(player.existEmptyRoom());
        assertEquals(3,player.getFamilyNumber());
    }
}