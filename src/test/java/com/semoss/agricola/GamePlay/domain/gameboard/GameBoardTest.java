package com.semoss.agricola.GamePlay.domain.gameboard;

import com.semoss.agricola.GamePlay.domain.AgricolaGame;
import com.semoss.agricola.GamePlay.domain.action.Event;
import com.semoss.agricola.GameRoomCommunication.domain.User;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

class GameBoardTest {
    @Test
    void shuffleEventsWithinRound_shouldShuffleEventsWithinSameRound() {
        // given
        AgricolaGame game = AgricolaGame.builder()
                .users(List.of(User.builder().username("test1").build()))
                .build();
        // 아그리콜라 이벤트가 현재 라운드보다 작을 경우 은닉되어 증가한 후에 검증
        for (int i = 0; i < 12; i++)
            game.increaseRound();
        List<Event> events = game.getGameBoard().getEvents();

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
    void givenStackActions_whenProcessStackEvent_thenResourceAccumulated() {
        // given
        AgricolaGame game = AgricolaGame.builder()
                .users(List.of(User.builder().username("test1").build()))
                .build();
        // 아그리콜라 이벤트가 현재 라운드보다 작을 경우 은닉되어 증가한 후에 검증
        for (int i = 0; i < 12; i++)
            game.increaseRound();
        GameBoard board = game.getGameBoard();
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