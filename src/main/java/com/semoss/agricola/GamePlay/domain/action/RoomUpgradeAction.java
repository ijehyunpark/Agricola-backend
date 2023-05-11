package com.semoss.agricola.GamePlay.domain.action;

import com.semoss.agricola.GamePlay.domain.player.Player;
import com.semoss.agricola.GamePlay.domain.resource.ResourceType;
import lombok.Getter;

public class RoomUpgradeAction implements Action {
    @Getter
    private ActionType actionType = ActionType.UPGRADE;
    /** used one each action */
    private ResourceType resourceType;
    private int resourceNum;
    /** use the number of room count */
    private int numEachUpgrade;

    public RoomUpgradeAction(ResourceType resourceType, int resourceNum, int numEachUpgrade){
        this.resourceType = resourceType;
        this.resourceNum = resourceNum;
        this.numEachUpgrade = numEachUpgrade;
    }

    @Override
    public boolean checkPrecondition(Player player) {
        ResourceType needResource;
        switch (player.getRoomType()){
            case WOOD -> needResource = ResourceType.CLAY;
            case CLAY -> needResource = ResourceType.STONE;
            default -> {
                return false;
            }
        }
        if(player.getResource(needResource) < (player.getRoomCount() * numEachUpgrade)) return false;
        return (player.getResource(resourceType) >= resourceNum);
    }

    @Override
    public boolean runAction() {
        return false;
    }

    @Override
    public boolean runAction(Player player) {
        ResourceType needResource;
        switch (player.getRoomType()){
            case WOOD -> needResource = ResourceType.CLAY;
            case CLAY -> needResource = ResourceType.STONE;
            default -> {
                return false;
            }
        }
        player.upgradeRoom();
        player.useResource(needResource,player.getRoomCount() * numEachUpgrade);
        player.useResource(resourceType,resourceNum);
        return true;
    }
}
