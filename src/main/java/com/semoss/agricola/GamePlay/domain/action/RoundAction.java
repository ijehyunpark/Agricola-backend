package com.semoss.agricola.GamePlay.domain.action;

/**
 * 라운드 시작마다 특정 작업을 수행하는 액션
 */
public interface RoundAction extends Action {
    /**
     * when game move on to the next round
     */
    boolean runRoundAction();
}
