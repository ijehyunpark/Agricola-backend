package com.semoss.agricola.GamePlay.domain.action;

import com.semoss.agricola.GamePlay.domain.History;
import com.semoss.agricola.GamePlay.domain.player.Player;
import com.semoss.agricola.GamePlay.domain.resource.AnimalStruct;
import com.semoss.agricola.GamePlay.domain.resource.ResourceStruct;
import com.semoss.agricola.GamePlay.domain.resource.ResourceStructInterface;
import com.semoss.agricola.GamePlay.domain.resource.ResourceType;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

/**
 * 자원이 누적해서 쌓이는 행동
 */
@Getter
public class StackResourceAction implements Action {
    private final ActionType actionType = ActionType.STACK;
    private final ResourceStruct stackResource;

    /**
     * Stack resource action - Resources stack each round.
     * @param resourceType  resource type to stack
     * @param stackCount number of resource to stack
     */
    @Builder
    public StackResourceAction(ResourceType resourceType, int stackCount) {
        this.stackResource = ResourceStruct.builder()
                .resource(resourceType)
                .count(stackCount)
                .build();
    }
}
