package com.semoss.agricola.GamePlay.domain.action;

import com.semoss.agricola.GamePlay.domain.player.Player;
import com.semoss.agricola.GamePlay.domain.resource.ResourceType;
import lombok.Builder;
import lombok.Getter;

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
     * @param y 씨를 뿌릴 밭의 y좌표
     * @param x 씨를 뿌릴 밭의 x좌표
     * @param resourceType 심을 자원
     * @return
     */
    public void runAction(Player player, int y, int x, ResourceType resourceType) {
        if (resourceType != ResourceType.GRAIN && resourceType != ResourceType.VEGETABLE)
            throw new RuntimeException("씨앗 자원이 아닙니다.");
        if(player.getResource(resourceType) < 1)
            throw new RuntimeException("씨앗 자원이 부족합니다.");

        //씨앗을 심는다.
        player.cultivate(y, x, resourceType);
    }
}
