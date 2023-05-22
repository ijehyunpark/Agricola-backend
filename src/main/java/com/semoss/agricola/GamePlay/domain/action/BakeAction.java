package com.semoss.agricola.GamePlay.domain.action;

import com.semoss.agricola.GamePlay.domain.card.CardDictionary;
import com.semoss.agricola.GamePlay.domain.card.MajorCard;
import com.semoss.agricola.GamePlay.domain.gameboard.ImprovementBoard;
import com.semoss.agricola.GamePlay.domain.player.Player;
import com.semoss.agricola.GamePlay.domain.resource.ResourceType;
import lombok.Builder;
import lombok.Getter;

/**
 * TODO: 빵 굽기 액션
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
     * @param improvementCard 주설비 카드
     */
    public void runAction(Player player, MajorCard improvementCard) {
        if(!player.hasCardInField(improvementCard.getCardID()))
            throw new RuntimeException("주설비를 가지고 있지 않습니다.");

        if(player.getResource(ResourceType.GRAIN) < 1)
            throw new RuntimeException("곡식이 부족합니다.");

        // 자원 교환
        player.useResource(ResourceType.GRAIN, 1);
        player.addResource(ResourceType.FOOD, improvementCard.getBakeEfficiency());
    }
}
