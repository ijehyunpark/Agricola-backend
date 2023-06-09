package com.semoss.agricola.GamePlay.domain.action.component;

import com.semoss.agricola.GamePlay.domain.card.BakeTrigger;
import com.semoss.agricola.GamePlay.domain.card.CardDictionary;
import com.semoss.agricola.GamePlay.domain.player.Player;
import com.semoss.agricola.GamePlay.domain.resource.ResourceType;
import com.semoss.agricola.GamePlay.exception.NotFoundException;
import lombok.Builder;
import lombok.Getter;

import java.util.Optional;

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
    public void runAction(Player player, BakeTrigger bakeImprovementCard, CardDictionary cardDictionary) {
        Optional<Player> owner = cardDictionary.getOwner(bakeImprovementCard);

        if(owner.isEmpty() || !owner.get().equals(player))
            throw new NotFoundException("주설비를 가지고 있지 않습니다.");

        // 자원 교환
        player.useResource(ResourceType.GRAIN, 1);
        player.addResource(ResourceType.FOOD, bakeImprovementCard.getBakeEfficiency());
    }
}
