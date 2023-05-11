package com.semoss.agricola.GamePlay.domain.action;

import com.semoss.agricola.GamePlay.domain.player.Player;
import lombok.Builder;
import lombok.Getter;

/**
 * 선공권 가져오기
 */
public class GetStartingPositionAction implements Action {
    @Getter
    private ActionType actionType = ActionType.STARTING;

    @Builder
    public GetStartingPositionAction() {
    }

    /**
     * 선공권 가져오는 행동의 전제조건은 없습니다.
     * @param player
     * @return true
     */
    @Override
    public boolean checkPrecondition(Player player) {
        return true;
    }

    /**
     * 선공권 가져오는 행동의 라운드 행동은 없습니다.
     * @return
     */
    @Override
    public boolean runAction() {
        return false;
    }

    /**
     * 선공권을 가져옵니다.
     * @param player
     * @return
     */
    @Override
    public boolean runAction(Player player) {
        player.setStartingTokenByTrue();
        return false;
    }
}
