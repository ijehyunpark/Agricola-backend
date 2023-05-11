package com.semoss.agricola.GamePlay.domain.action;

import com.semoss.agricola.GamePlay.domain.player.Player;
import com.semoss.agricola.GamePlay.domain.resource.ResourceStruct;
import lombok.Builder;
import lombok.Getter;

public class BasicAction implements Action {
    @Getter
    private ActionType actionType;
    private ResourceStruct resource;

    /**
     * basic resource action
     * @param resource type and amount of resource to add
     */
    @Builder
    public BasicAction(ResourceStruct resource) {
        this.actionType = ActionType.BASIC;
        this.resource = resource;
    }

    /**
     * basic resource action - add resource to player
     * @param player player who get resource
     * @return always true
     */
    @Override
    public boolean runAction(Player player) {
        player.addResource(resource);
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
}