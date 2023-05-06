package com.semoss.agricola.action;

import com.semoss.agricola.mainflow.Player;
import com.semoss.agricola.mainflow.ResourceType;

public class StackAction implements Action{
    private ActionType actionType;
    private ResourceType resourceType;
    private int resourceNum;
    private int stackResource;

    /**
     * Stack resource action - Resources stack each round.
     * @param rt  resource type to add
     * @param num amount of resource
     */
    public StackAction(ResourceType rt, int num) {
        actionType = ActionType.STACK;
        resourceType = rt;
        resourceNum = num;
        stackResource = num;
    }

    /**
     * stack resources
     * @return always true
     */
    @Override
    public boolean runAction() {
        stackResource += resourceNum;
        return true;
    }

    /**
     * stack resource action - add resource to player
     * @param player player who get resource
     * @return always true
     */
    @Override
    public boolean runAction(Player player) {
        player.addResource(resourceType,stackResource);
        stackResource = 0;
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

    /**
     * how many resources were stacked
     * @return amount of stacked resources
     */
    public int getStackResource() {
        return stackResource;
    }
}