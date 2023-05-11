package com.semoss.agricola.GamePlay.domain.action;

import com.semoss.agricola.GamePlay.domain.player.Player;

public interface Action {
    ActionType actionType = ActionType.EMPTY;

    /**
     * Check if the player satisfies the precondition
     */
    boolean checkPrecondition(Player player);

    /**
     * when game move on to the next round
     */
    boolean runAction();

    /**
     * when players select this action
     */
    boolean runAction(Player player);
    ActionType getActionType();
}
