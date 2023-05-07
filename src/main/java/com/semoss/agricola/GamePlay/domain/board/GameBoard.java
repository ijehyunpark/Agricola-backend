package com.semoss.agricola.GamePlay.domain.board;

import com.semoss.agricola.GamePlay.domain.action.Event;
import lombok.Getter;

import java.util.List;
@Getter
public class GameBoard {
    private Event[] roundAction;
    private Object roundStock;
    private List<Event> basicAction;
    private ImprovementBoard improvementBoard;

    public GameBoard() {
        // TODO
        shuffleRoundAction();
    }

    /**
     * 라운드 액션을 셔플한다.
     */
    private void shuffleRoundAction(){
        // TODO
    }
}
