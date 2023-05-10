package com.semoss.agricola.GamePlay.domain.action;

import com.semoss.agricola.GamePlay.domain.player.Player;
import com.semoss.agricola.GamePlay.domain.resource.ResourceType;
import lombok.Getter;

public class BakeAction implements Action {

    @Getter
    private ActionType actionType;
    private ResourceType resourceType;
    private ResourceType bread = ResourceType.FOOD;
    private int breadNum;

    public BakeAction(int breadNum){
        actionType = ActionType.BAKE;
        resourceType = ResourceType.GRAIN;
        this.breadNum = breadNum;
    }

    @Override
    public boolean checkPrecondition(Player player) {
        return player.getResource(resourceType) >= 1;
    }

    @Override
    public boolean runAction() {
        return true;
    }

    @Override
    public boolean runAction(Player player) {
        player.useResource(resourceType,1);
        player.addResource(bread,breadNum);
        return true;
    }
}
