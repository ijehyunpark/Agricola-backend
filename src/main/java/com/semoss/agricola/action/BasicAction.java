package com.semoss.agricola.action;

import com.semoss.agricola.mainflow.Player;
import com.semoss.agricola.mainflow.ResourceType;

public class BasicAction implements Action {
    private ActionType actionType;
    private ResourceType resourceType;
    private int resourceNum;

    public BasicAction(ResourceType rt, int num) {
        actionType = ActionType.BASIC;
        resourceType = rt;
        resourceNum = num;
    }

    @Override
    public boolean runAction(Player player) {
        player.addResource(resourceType,resourceNum);
        return true;
    }

    @Override
    public boolean runAction() {
        return false;
    }
}
