package com.semoss.agricola.GamePlay.domain.action.component;

import com.semoss.agricola.GamePlay.domain.resource.ResourceStruct;
import lombok.Builder;
import lombok.Getter;

/**
 * 자원이 누적해서 쌓이는 행동
 */
@Getter
public class StackResourceAction implements StackAction {
    private final ActionType actionType = ActionType.STACK;
    private final ResourceStruct stackResource;

    /**
     * Stack resource action - Resources stack each round.
     */
    @Builder
    public StackResourceAction(ResourceStruct resource) {
        this.stackResource = resource;
    }
}
