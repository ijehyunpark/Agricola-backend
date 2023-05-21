package com.semoss.agricola.GamePlay.domain.action;

import com.semoss.agricola.GamePlay.domain.player.Player;
import com.semoss.agricola.GamePlay.domain.resource.ResourceStruct;
import lombok.Builder;
import lombok.Getter;

@Getter
public class BasicAction implements SimpleAction {
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
     *
     * @param player player who get resource
     */
    @Override
    public void runAction(Player player) {
        player.addResource(resource);
    }
}