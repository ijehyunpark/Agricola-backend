package com.semoss.agricola.GamePlay.domain.gameboard;

import com.semoss.agricola.GamePlay.domain.action.Event;
import jakarta.validation.constraints.AssertTrue;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

class GameBoardTest {

    @Test
    void shuffleEventsWithinRound_shouldShuffleEventsWithinSameRound() {
        // given
        GameBoard board = GameBoard.builder().build();
        List<Event> events = board.getEvents();

        // when

        // then
        boolean isSorted = true;
        boolean isMatched = true;
        for (int i = 0; i < events.size() - 1; i++) {
            if (events.get(i).getRound() > events.get(i + 1).getRound() ||
                    events.get(i).getRoundGroup() > events.get(i + 1).getRoundGroup()) {
                isSorted = false;
                break;
            }
            if(events.get(i).getRoundGroup() == 0 && events.get(i).getRound() != 0 ||
                    events.get(i).getRoundGroup() != 0 && events.get(i).getRound() == 0){
                isMatched = false;
            }
//            System.out.print(events.get(i).getRound());
//            System.out.println(events.get(i).getRoundGroup());
        }
        // 정렬 되있는지 확인
        assertTrue(isSorted);
        // 라운드 배치가 되어 있는지 확인
        assertTrue(isMatched);
    }

    @Test
    void givenStackActions_whenProcessStackEvent_thenResourceAccumulated() {
        // given
        GameBoard board = GameBoard.builder().build();
        List<Event> events = board.getEvents();

        // when
        board.processStackEvent(10);

        // then
        boolean dirtyChk = false;
        for(Event event : events){
            if(event.getStacks().size() != 0)
                dirtyChk = true;
        }
        assertTrue(dirtyChk);
    }
}