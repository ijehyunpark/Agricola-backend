package com.semoss.agricola.GamePlay.domain.action;

import com.semoss.agricola.GamePlay.domain.History;
import com.semoss.agricola.GamePlay.domain.player.Player;
import com.semoss.agricola.GamePlay.domain.player.RoomType;
import com.semoss.agricola.GamePlay.domain.resource.ResourceStruct;
import com.semoss.agricola.GamePlay.exception.IllgalRequestException;
import com.semoss.agricola.GamePlay.exception.ResourceLackException;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.Map;

@Getter
public class RoomUpgradeAction implements SimpleAction {
    private final ActionType actionType = ActionType.UPGRADE;
    /** upgrade cost {Upgrade room rank, costs] */
    private final Map<RoomType, List<ResourceStruct>> costs;

    @Builder
    public RoomUpgradeAction(Map<RoomType, List<ResourceStruct>> costs){
        this.costs = costs;
    }

    public boolean checkPrecondition(Player player, RoomType nextRank) {
        // 필요 자원보다 적을 경우 false 반환
        for(ResourceStruct cost : costs.get(nextRank)){
            if(player.getResource(cost.getResource()) < player.getRoomCount() * cost.getCount())
                return false;
        }

        return true;
    }

    @Override
    public void runAction(Player player, History history) {
        // 업그레이드할 방을 찾는다.
        RoomType nextRank;
        switch (player.getRoomType()){
            case WOOD -> nextRank = RoomType.CLAY;
            case CLAY -> nextRank = RoomType.STONE;
            default -> {
                throw new IllgalRequestException("최고 랭크 방은 업그레이드가 불가능합니다.");
            }
        }

        if(!checkPrecondition(player, nextRank))
            throw new ResourceLackException("방 업그레이드가 불가능합니다.");

        // 비용을 지불한다.
        for(ResourceStruct cost : costs.get(nextRank)){
            player.useResource(cost.getResource(), player.getRoomCount() * cost.getCount());
        }

        // 방을 업그레이드 한다.
        player.upgradeRoom();
    }
}
