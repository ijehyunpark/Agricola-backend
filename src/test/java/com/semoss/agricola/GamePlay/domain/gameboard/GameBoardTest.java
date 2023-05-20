package com.semoss.agricola.GamePlay.domain.gameboard;

import com.semoss.agricola.GamePlay.domain.AgricolaGame;
import com.semoss.agricola.GamePlay.domain.action.Event;
import com.semoss.agricola.GamePlay.domain.action.GetStartingPositionAction;
import com.semoss.agricola.GamePlay.domain.player.Player;
import com.semoss.agricola.GameRoomCommunication.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GameBoardTest {
    @Mock
    AgricolaGame game;
    @InjectMocks
    GameBoard board;

    @BeforeEach
    void setUp() {
        board = GameBoard.builder()
                .game(game)
                .build();
    }
    @Test
    @DisplayName("라운드 셔플 테스트")
    void shuffleEventsWithinRound_shouldShuffleEventsWithinSameRound() {
        // given
        when(game.getRound()).thenReturn(100);
        List<Event> events = board.getEvents();

        // when

        // then
        boolean isSorted = true;
        boolean isMatched = true;

        for (int i = 0; i < events.size() - 1; i++) {
            if (events.get(i).getRound() > events.get(i + 1).getRound() ||
                    events.get(i).getRoundGroup() > events.get(i + 1).getRoundGroup()) {
                isSorted = false;
            }
            if(events.get(i).getRoundGroup() == 0 && events.get(i).getRound() != 0 ||
                    events.get(i).getRoundGroup() != 0 && events.get(i).getRound() == 0){
                isMatched = false;
            }
            System.out.print("round: " + events.get(i).getRound() + "  ::  ");
            System.out.println("roundGroup: " + events.get(i).getRoundGroup());
        }
        // 정렬 되있는지 확인
        assertTrue(isSorted);
        // 라운드 배치가 되어 있는지 확인
        assertTrue(isMatched);
    }

    @Test
    @DisplayName("스택 이벤트 처리 테스트")
    void givenStackActions_whenProcessStackEvent_thenResourceAccumulated() {
        // given
        when(game.getRound()).thenReturn(100);
        List<Event> events = board.getEvents();

        // when
        board.processStackEvent();

        // then
        boolean dirtyChk = false;
        for(Event event : events){
            if(event.getStacks().size() != 0)
                dirtyChk = true;
        }
        assertTrue(dirtyChk);
    }

    @Test
    @Disabled
    @DisplayName("예약 자원 전달 테스트")
    void processReservationResourceTest() {
        // given

        // when

        // then

    }
}