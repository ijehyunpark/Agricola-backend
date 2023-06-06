package com.semoss.agricola.GamePlay.domain.action;

import com.semoss.agricola.GamePlay.domain.card.BakeTrigger;
import com.semoss.agricola.GamePlay.domain.player.Player;
import com.semoss.agricola.GamePlay.domain.resource.ResourceType;
import com.semoss.agricola.GamePlay.exception.NotFoundException;
import com.semoss.agricola.GamePlay.exception.ResourceLackException;
import lombok.Builder;
import lombok.Getter;

/**
 * 빵 굽기 액션
 */
public class BakeAction implements Action {

    @Getter
    private final ActionType actionType = ActionType.BAKE;

    @Builder
    public BakeAction(){
    }

    /**
     * 빵 굽는다.
     * @param player 빵굽는 플레이어
     * @param bakeImprovementCard 주설비 카드
     */
    public void runAction(Player player, BakeTrigger bakeImprovementCard) {
        if(!player.hasCardInField(bakeImprovementCard.getCardID()))
            throw new NotFoundException("주설비를 가지고 있지 않습니다.");

        if(player.getResource(ResourceType.GRAIN) < 1)
            throw new ResourceLackException("곡식이 부족합니다.");

        // 자원 교환
        player.useResource(ResourceType.GRAIN, 1);
        player.addResource(ResourceType.FOOD, bakeImprovementCard.getBakeEfficiency());
    }
}
