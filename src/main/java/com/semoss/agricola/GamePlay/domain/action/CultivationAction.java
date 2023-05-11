package com.semoss.agricola.GamePlay.domain.action;

import com.semoss.agricola.GamePlay.domain.player.Player;
import lombok.Builder;
import lombok.Getter;

/**
 * 씨 뿌리기 액션
 */
public class CultivationAction implements Action {

    @Getter
    private final ActionType actionType = ActionType.CULTIVATION;

    @Builder
    public CultivationAction() {
    }

    /**
     * TODO: 씨앗 심기 전 빈 밭인지 검증
     * @param player
     * @return
     */
    @Override
    public boolean checkPrecondition(Player player) {
        return false;
    }

    /**
     * 씨 뿌리기는 라운드 종료 액션을 수행하지 않습니다.
     * @return
     */
    @Override
    public boolean runAction() {
        return false;
    }

    /**
     * TODO: 플레이어 밭에 씨를 뿌립니다.
     * @param player
     * @return
     */
    @Override
    public boolean runAction(Player player) {
        return false;
    }
}
