package com.semoss.agricola.GamePlay.domain.action;

import com.semoss.agricola.GamePlay.domain.player.FieldType;
import com.semoss.agricola.GamePlay.domain.player.Player;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class IncreaseFamilyTest {
    Player player;
    IncreaseFamily increaseFamily;

    @BeforeEach
    void setUp() {
        player = Player.builder()
                .userId(1234L)
                .isStartPlayer(true)
                .build();
    }
    @Test
    @DisplayName("runAction: 빈 방이 있는 경우 가족 늘리기 테스트 - 성공")
    void runActionPrecondition2() {
        // given
        increaseFamily = IncreaseFamily.builder()
                .precondition(true)
                .build();

        player.buildField(0, 0, FieldType.ROOM);
        assertTrue(player.existEmptyRoom());

        // when
        increaseFamily.runAction(player);

        // then
        assertFalse(player.existEmptyRoom());
        assertEquals(3,player.getFamilyNumber());
    }

    @Test
    @DisplayName("runAction: 빈 방이 있는 경우 가족 늘리기 테스트 - 실패")
    void runActionPrecondition1() {
        // given
        increaseFamily = IncreaseFamily.builder()
                .precondition(true)
                .build();

        assertFalse(player.existEmptyRoom());

        // when
        assertThrows(RuntimeException.class, () -> increaseFamily.runAction(player));

        // then
        assertFalse(player.existEmptyRoom());
        assertEquals(2,player.getFamilyNumber());

    }

    @Test
    @DisplayName("runAction: 급한 가족 늘리기 테스트")
    void runActionNotPrecondition2() {
        // given
        increaseFamily = IncreaseFamily.builder()
                .precondition(false)
                .build();

        assertFalse(player.existEmptyRoom());

        // when
        increaseFamily.runAction(player);

        // then
        assertFalse(player.existEmptyRoom());
        assertEquals(3,player.getFamilyNumber());
    }
}