package com.semoss.agricola.GamePlay.domain.gameboard;

import com.semoss.agricola.GamePlay.domain.AgricolaGame;
import com.semoss.agricola.GamePlay.domain.action.Event;
import com.semoss.agricola.GamePlay.domain.action.implement.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GameBoardTest {
    AgricolaGame game;
    ImprovementBoard improvementBoard;
    GameBoard board;

    List<Event> buildEvents() {
        return List.of(
                Event.builder().action(new Action1()).build(),
                Event.builder().action(new Action2()).build(),
                Event.builder().action(new Action3()).build(),
                Event.builder().action(new Action4()).build(),
                Event.builder().action(new Action5()).build(),
                Event.builder().action(new Action6()).build(),
                Event.builder().action(new Action7()).build(),
                Event.builder().action(new Action8()).build(),
                Event.builder().action(new Action9()).build(),
                Event.builder().action(new Action10()).build(),
                Event.builder().action(new Action11()).build(),
                Event.builder().action(new Action12()).build(),
                Event.builder().action(new Action13()).build(),
                Event.builder().action(new Action14()).build(),
                Event.builder().action(new Action15()).build(),
                Event.builder().action(new Action16()).build(),
                Event.builder().action(new Action17()).build(),
                Event.builder().action(new Action18()).build(),
                Event.builder().action(new Action19()).build(),
                Event.builder().action(new Action20()).build(),
                Event.builder().action(new Action21()).build(),
                Event.builder().action(new Action22()).build(),
                Event.builder().action(new Action23()).build(),
                Event.builder().action(new Action24()).build());
    }

    @BeforeEach
    void setUp() {
        game = mock(AgricolaGame.class);
        improvementBoard = mock(ImprovementBoard.class);
        board = new GameBoard(game, buildEvents(), improvementBoard);
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
                    events.get(i).getAction().getRoundGroup() > events.get(i + 1).getAction().getRoundGroup()) {
                isSorted = false;
            }
            if(events.get(i).getAction().getRoundGroup() == 0 && events.get(i).getRound() != 0 ||
                    events.get(i).getAction().getRoundGroup() != 0 && events.get(i).getRound() == 0){
                isMatched = false;
            }
            System.out.print("round: " + events.get(i).getRound() + "  ::  ");
            System.out.println("roundGroup: " + events.get(i).getAction().getRoundGroup());
        }
        System.out.print("round: " + events.get(events.size() - 1).getRound() + "  ::  ");
        System.out.println("roundGroup: " + events.get(events.size() - 1).getAction().getRoundGroup());
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