package com.semoss.agricola.GamePlay.domain.action;

import com.semoss.agricola.GamePlay.domain.player.Player;

/**
 * 추가 입력 없이 수행할 수 있는 액션
 */
public interface SimpleAction extends Action {

    /**
     * Check if the player satisfies the precondition
     */
    boolean checkPrecondition(Player player);

    /**
     * when players select this action
     */
    boolean runAction(Player player);
}
