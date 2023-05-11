package com.semoss.agricola.GamePlay.domain.action;

import com.semoss.agricola.GamePlay.domain.player.Player;
import com.semoss.agricola.GamePlay.domain.resource.ResourceStruct;
import com.semoss.agricola.GamePlay.domain.resource.ResourceType;
import lombok.Builder;
import lombok.Getter;

/**
 * 자원이 누적해서 쌓이는 행동
 */
public class StackAction implements Action {
    @Getter
    private final ActionType actionType = ActionType.STACK;
    private final ResourceStruct resource;
    @Getter
    private int stackCount;

    /**
     * Stack resource action - Resources stack each round.
     * @param resourceType  resource type to stack
     * @param stackCount number of resource to stack
     */
    @Builder
    public StackAction(ResourceType resourceType, int stackCount) {
        this.resource = ResourceStruct.builder()
                .resource(resourceType)
                .count(0)
                .build();
        this.stackCount = stackCount;
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
        this.resource.addResource(this.stackCount);
        return true;
    }

    /**
     * stack resource action - add resource to player
     * @param player player who get resource
     * @return always true
     */
    @Override
    public boolean runAction(Player player) {
        player.addResource(this.resource);
        this.resource.setCount(0);
        return true;
    }
}
