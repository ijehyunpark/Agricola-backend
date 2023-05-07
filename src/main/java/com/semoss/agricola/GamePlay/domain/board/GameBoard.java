package com.semoss.agricola.GamePlay.domain.board;

import com.semoss.agricola.GamePlay.domain.action.Event;
import lombok.Getter;

import java.util.List;

/**
 * 아그리콜라 메인 게임 보드
 */
@Getter
public class GameBoard {
    private List<Event> events;
    private Object roundStock;
    private ImprovementBoard improvementBoard;

    public GameBoard() {
        // TODO : 게임 보드 설계
        shuffleRoundAction();
    }

    /**
     * 라운드 액션을 셔플한다.
     */
    private void shuffleRoundAction(){
        // TODO : 라운드 액션 셔플
    }
}
