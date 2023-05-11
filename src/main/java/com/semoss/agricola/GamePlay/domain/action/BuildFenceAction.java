package com.semoss.agricola.GamePlay.domain.action;

import com.semoss.agricola.GamePlay.domain.player.Player;
import lombok.Builder;
import lombok.Getter;

/**
 * 울타리 건설 액션
 * 울타리를 건설해 봐요.
 * e.g 집, 외양간 여러개 짓기
 */
//TODO : implement fence actions
public class BuildFenceAction implements Action {
    @Getter
    private final ActionType actionType = ActionType.BUILD;
    @Builder
    public BuildFenceAction() {

    }
    
    @Override
    public boolean checkPrecondition(Player player) {
        return false;
    }

    @Override
    public boolean runAction() {
        return false;
    }

    @Override
    public boolean runAction(Player player) {
        return false;
    }
}
