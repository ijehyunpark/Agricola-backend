package com.semoss.agricola.GamePlay.domain.Action;

import com.semoss.agricola.GamePlay.domain.Player;
import com.semoss.agricola.GamePlay.domain.ResourceType;

public class BakeAction implements Action{
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

    @Override
    public ActionType getActionType() {
        return actionType;
    }
}
