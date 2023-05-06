package com.semoss.agricola.action;

import com.semoss.agricola.mainflow.Player;

public interface Action {
    ActionType actionType = ActionType.EMPTY;

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
