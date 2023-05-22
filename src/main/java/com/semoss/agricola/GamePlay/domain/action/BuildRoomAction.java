package com.semoss.agricola.GamePlay.domain.action;

import com.semoss.agricola.GamePlay.domain.player.FieldType;
import com.semoss.agricola.GamePlay.domain.player.Player;
import com.semoss.agricola.GamePlay.domain.resource.ResourceStruct;
import com.semoss.agricola.GamePlay.domain.resource.ResourceType;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * 집 건설 액션
 * 1개의 빈 필드에 새로운 건축물을 건설한다.
 * e.g 집 건설
 */
@Getter
public class BuildRoomAction extends BuildSimpleAction {

    public BuildRoomAction() {
        super(FieldType.ROOM, new ArrayList<>(), -1);

    }

    private void setRequirements(ResourceType resourceType){
        setRequirements(List.of(
                ResourceStruct.builder()
                        .resource(resourceType)
                        .count(5)
                        .build(),
                ResourceStruct.builder()
                        .resource(ResourceType.GRAIN)
                        .count(2)
                        .build()));
    }

    /**
     * 플레이어의 필드에 집을 추가한다.
     * @param player 건설 작업을 수행할 플레이어
     */
    public void runAction(Player player, int y, int x) {
        switch (player.getRoomType()) {
            case WOOD -> {
                setRequirements(ResourceType.WOOD);
            }
            case CLAY -> {
                setRequirements(ResourceType.CLAY);
            }
            case STONE -> {
                setRequirements(ResourceType.STONE);
            }
        }
        super.runAction(player, y, x);
    }

}
