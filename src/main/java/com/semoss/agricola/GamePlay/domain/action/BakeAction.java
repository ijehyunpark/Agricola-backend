package com.semoss.agricola.GamePlay.domain.action;

import com.semoss.agricola.GamePlay.domain.gameboard.ImprovementBoard;
import com.semoss.agricola.GamePlay.domain.player.Player;
import lombok.Builder;
import lombok.Getter;

/**
 * TODO: 빵 굽기 액션
 */
public class BakeAction implements MultiInputAction {

    @Getter
    private final ActionType actionType = ActionType.BAKE;

    @Builder
    public BakeAction(){
    }

    private boolean checkPrecondition(Player player, ImprovementBoard improvementBoard) {
        // TODO: 플레이어가 해당 주설비를 가지고 있는지 확인
        return false;
    }

    /**
     * 빵 굽는다.
     * @param player
     * @param ImprovementCard
     * @return
     */
    public void runAction(Player player, Object ImprovementCard) {
        // TODO: 플레이어가 해당 주설비를 사용하여 자원을 교환한다.
        // TODO: ImprovementCard 보단 1. ImprovementBoardType을 만들어 화로, 화덕, 흙가마, 돌가마만 받아 player에서 가져와 확인 || 2. cardId를 가져와 검증
        throw new RuntimeException("미구현");
    }
}
