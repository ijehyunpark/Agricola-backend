package com.semoss.agricola.GamePlay.domain.Action;

import com.semoss.agricola.GamePlay.domain.Player;

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
