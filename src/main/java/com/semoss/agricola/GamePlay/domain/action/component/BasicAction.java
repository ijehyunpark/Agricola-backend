package com.semoss.agricola.GamePlay.domain.action.component;

import com.semoss.agricola.GamePlay.domain.History;
import com.semoss.agricola.GamePlay.domain.player.Player;
import com.semoss.agricola.GamePlay.domain.resource.ResourceStruct;
import lombok.Builder;
import lombok.Getter;
import lombok.Singular;

import java.util.List;

@Getter
@Builder
public class BasicAction implements SimpleAction {
    private final ActionType actionType = ActionType.BASIC;
    @Singular
    private List<ResourceStruct> resources;

    /**
     * basic resource action - add resource to player
     *
     * @param player player who get resource
     */
    @Override
    public void runAction(Player player, History history) {
        player.addResource(resources);
        for (ResourceStruct resource : resources) {
            history.writeResourceChange(resource);
        }
    }
}