package com.semoss.agricola.GamePlay.domain.action;

import com.semoss.agricola.GamePlay.domain.player.Player;
import com.semoss.agricola.GamePlay.domain.resource.ResourceStruct;
import com.semoss.agricola.GamePlay.domain.resource.ResourceType;
import lombok.Builder;
import lombok.Getter;

/**
 * 자원이 누적해서 쌓이는 행동
 */
public class StackAction implements SimpleAction {
    @Getter
    private final ActionType actionType = ActionType.STACK;
    @Getter
    private final ResourceStruct stackResource;

    /**
     * Stack resource action - Resources stack each round.
     * @param resourceType  resource type to stack
     * @param stackCount number of resource to stack
     */
    @Builder
    public StackAction(ResourceType resourceType, int stackCount) {
        this.stackResource = ResourceStruct.builder()
                .resource(resourceType)
                .count(stackCount)
                .build();
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
     * stack resource action - do nothing, Stack actions do not do any additional work on player actions.
     * @param player player who get resource
     * @return always true
     */
    @Override
    public boolean runAction(Player player) {
        return true;
    }
}
