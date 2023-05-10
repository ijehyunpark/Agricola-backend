package com.semoss.agricola.GamePlay.domain.gameboard;

// TODO : Event 개선 후 테스트 코드 변경
class GameBoardTest {

//    @Test
//    void shuffleEventsWithinRound_shouldShuffleEventsWithinSameRound() {
//        // given
//        List<Event> events = new ArrayList<>();
//        events.add(Event.builder().round(0).playerUsed(0L).build());
//        events.add(Event.builder().round(0).playerUsed(1L).build());
//        events.add(Event.builder().round(1).playerUsed(2L).build());
//        events.add(Event.builder().round(2).playerUsed(3L).build());
//        events.add(Event.builder().round(2).playerUsed(4L).build());
//        events.add(Event.builder().round(2).playerUsed(5L).build());
//        events.add(Event.builder().round(3).playerUsed(6L).build());
//        events.add(Event.builder().round(3).playerUsed(7L).build());
//        events.add(Event.builder().round(3).playerUsed(8L).build());
//        events.add(Event.builder().round(3).playerUsed(9L).build());
//        GameBoard board = GameBoard.builder().events(events).build();
//
//        // when
//        board.shuffleEventsWithinRound();
//
//        // then
//        for (int i = 0; i < events.size() - 1; i++) {
//            System.out.print(events.get(i).getRound());
//            System.out.println(events.get(i).getPlayerUsed());
//            int currRound = events.get(i).getRound();
//            int nextRound = events.get(i + 1).getRound();
//        }
//    }
}