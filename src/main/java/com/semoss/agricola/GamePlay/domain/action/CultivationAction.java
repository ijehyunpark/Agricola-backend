package com.semoss.agricola.GamePlay.domain.action;

import com.semoss.agricola.GamePlay.domain.player.Player;
import com.semoss.agricola.GamePlay.domain.resource.ResourceType;
import com.semoss.agricola.GamePlay.dto.CultivationActionExtentionRequest;
import lombok.Builder;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;

/**
 * 경작 이벤트
 */

@Getter
public class CultivationAction implements Action {
    private final ActionType actionType = ActionType.CULTIVATION;

    @Builder
    public CultivationAction() {
    }

    /**
     * @param player 씨를 뿌릴 플레이어
     */
    public void runAction(Player player, List<CultivationActionExtentionRequest> requests) {
        boolean hasDuplicateValues = requests.stream()
                .map(request -> new Object[]{request.getX(), request.getY(), request.getResourceType()})
                .anyMatch(request -> {
                    long count = requests.stream()
                            .map(otherRequest -> new Object[]{otherRequest.getX(), otherRequest.getY(), otherRequest.getResourceType()})
                            .filter(arr -> Arrays.equals(arr, request))
                            .count();
                    return count > 1;
                });


        if(hasDuplicateValues)
            throw new RuntimeException("중복된 요청이 있습니다.");


        int grainCount = 0;
        int vegetableCount = 0;
        for(CultivationActionExtentionRequest request : requests){
            if (request.getResourceType() != ResourceType.GRAIN && request.getResourceType() != ResourceType.VEGETABLE)
                throw new RuntimeException("씨앗 자원이 아닙니다.");
            if(request.getResourceType() == ResourceType.GRAIN)
                grainCount++;
            else
                vegetableCount++;
        }

        if(player.getResource(ResourceType.GRAIN) < grainCount || player.getResource(ResourceType.VEGETABLE) < vegetableCount)
            throw new RuntimeException("씨앗 자원이 부족합니다.");

        //씨앗을 심는다.
        player.cultivate(requests);
        requests.forEach(request -> player.useResource(request.getResourceType(), 1));
    }
}
