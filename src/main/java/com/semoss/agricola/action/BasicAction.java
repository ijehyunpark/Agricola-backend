package com.semoss.agricola.action;

import com.semoss.agricola.mainflow.Player;
import com.semoss.agricola.mainflow.ResourceType;

public class BasicAction implements Action {
    private ActionType actionType;
    private ResourceType resourceType;
    private int resourceNum;

    /**
     * basic resource action
     * @param rt resource type to add
     * @param num amount of resource
     */
    public BasicAction(ResourceType rt, int num) {
        actionType = ActionType.BASIC;
        resourceType = rt;
        resourceNum = num;
    }

    /**
     * basic resource action - add resource to player
     * @param player player who get resource
     * @return always true
     */
    @Override
    public boolean runAction(Player player) {
        player.addResource(resourceType,resourceNum);
        return true;
    }

    /**
     * Check if the player satisfies the precondition
     * @return always true
     */
    @Override
    public boolean checkPrecondition(Player player) {
        return true;
    }

    /**
     * do nothing
     * @return always return true
     */
    @Override
    public boolean runAction() {
        return true;
    }

    /**
     * return action type
     * @return action type - basic
     */
    @Override
    public ActionType getActionType() {
        return actionType;
    }
}
