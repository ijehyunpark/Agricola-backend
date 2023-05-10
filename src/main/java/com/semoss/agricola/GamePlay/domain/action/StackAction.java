package com.semoss.agricola.GamePlay.domain.action;

import com.semoss.agricola.GamePlay.domain.player.Player;
import com.semoss.agricola.GamePlay.domain.resource.ResourceType;
import lombok.Getter;

public class StackAction implements Action {
    @Getter
    private ActionType actionType;
    private ResourceType resourceType;
    private int resourceNum;
    @Getter
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
     * Check if the player satisfies the precondition
     * @return always true
     */
    @Override
    public boolean checkPrecondition(Player player) {
        return true;
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
}
