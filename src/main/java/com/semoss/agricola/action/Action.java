package com.semoss.agricola.action;

import com.semoss.agricola.mainflow.Player;

public interface Action {
    ActionType actionType = ActionType.EMPTY;
    boolean runAction();
    boolean runAction(Player player);
}
