package com.semoss.agricola.GamePlay.domain.action;

import com.semoss.agricola.GamePlay.domain.History;
import com.semoss.agricola.GamePlay.domain.player.Player;
import com.semoss.agricola.GamePlay.domain.resource.ResourceStruct;
import com.semoss.agricola.GamePlay.domain.resource.ResourceType;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.Map;

@Getter
public class RoomUpgradeAction implements SimpleAction {
    private final ActionType actionType = ActionType.UPGRADE;
    /** upgrade cost [before room rank, costs] */
    private final Map<ResourceType, List<ResourceStruct>> costs;

    @Builder
    public RoomUpgradeAction(Map<ResourceType, List<ResourceStruct>> costs){
        this.costs = costs;
    }

    public boolean checkPrecondition(Player player) {
        // 다음 room rank를 찾는다, 최고 랭크일 경우 false 반환
        ResourceType needResource;
        switch (player.getRoomType()){
            case WOOD -> needResource = ResourceType.CLAY;
            case CLAY -> needResource = ResourceType.STONE;
            default -> {
                return false;
            }
        }

        // 필요 자원보다 적을 경우 false 반환
        for(ResourceStruct cost : costs.get(needResource)){
            if(player.getResource(cost.getResource()) < player.getRoomCount() * cost.getCount())
                return false;
        }

        return true;
    }

    @Override
    public void runAction(Player player, History history) {
        if(!checkPrecondition(player))
            throw new RuntimeException("방 업그레이드가 불가능합니다.");

        // 업그레이드할 방을 찾는다.
        ResourceType needResource;
        switch (player.getRoomType()){
            case WOOD -> needResource = ResourceType.CLAY;
            case CLAY -> needResource = ResourceType.STONE;
            default -> {
                throw new RuntimeException("잘못된 방 형식입니다.");
            }
        }

        // 비용을 지불한다.
        for(ResourceStruct cost : costs.get(needResource)){
            player.useResource(cost.getResource(), player.getRoomCount() * cost.getCount());
        }

        // 방을 업그레이드 한다.
        player.upgradeRoom();
    }
}
