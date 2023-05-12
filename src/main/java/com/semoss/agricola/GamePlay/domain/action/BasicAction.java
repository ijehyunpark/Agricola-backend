package com.semoss.agricola.GamePlay.domain.action;

import com.semoss.agricola.GamePlay.domain.player.Player;
import com.semoss.agricola.GamePlay.domain.resource.ResourceStruct;
import lombok.Builder;
import lombok.Getter;

public class BasicAction implements SimpleAction {
    @Getter
    private final ActionType actionType = ActionType.BASIC;
    private final ResourceStruct resource;

    /**
     * basic resource action
     * @param resource type and amount of resource to add
     */
    @Builder
    public BasicAction(ResourceStruct resource) {
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
}