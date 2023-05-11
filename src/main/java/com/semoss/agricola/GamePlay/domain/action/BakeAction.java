package com.semoss.agricola.GamePlay.domain.action;

import com.semoss.agricola.GamePlay.domain.player.Player;
import lombok.Builder;
import lombok.Getter;

/**
 * 빵 굽기 액션
 */
public class BakeAction implements Action {

    @Getter
    private final ActionType actionType = ActionType.BAKE;

    @Builder
    public BakeAction(){
    }

    @Override
    public boolean checkPrecondition(Player player) {
        // TODO: 플레이어가 화로, 화덕, 흙가마 주설비를 가지고 있는지 확인
        return false;
    }

    /**
     * 빵 굽기는 라운드 종료 액션을 수행하지 않습니다.
     * @return
     */
    @Override
    public boolean runAction() {
        return true;
    }

    /**
     * 빵 굽는다.
     * @param player
     * @return
     */
    @Override
    public boolean runAction(Player player) {
        // TODO: 플레이어가 소유한 화로, 화덕, 흙가마 주설비를 사용하여 자원을 교환한다.
        return true;
    }
}
