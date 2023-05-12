package com.semoss.agricola.GamePlay.domain.action;

import com.semoss.agricola.GamePlay.domain.player.Player;

/**
 * 추가적인 입력을 필요로 하는 액션
 */
public interface MultiInputAction extends Action {
    /**
     * Check if the player satisfies the precondition
     */
    boolean checkPrecondition(Player player, Object detail);

    /**
     * when players select this action
     */
    boolean runAction(Player player, Object detail);
}
