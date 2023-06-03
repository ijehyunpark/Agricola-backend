package com.semoss.agricola.GamePlay.domain.card.Minorcard;

import com.semoss.agricola.GamePlay.domain.History;
import com.semoss.agricola.GamePlay.domain.action.implement.ActionName;
import com.semoss.agricola.GamePlay.domain.card.CardType;
import com.semoss.agricola.GamePlay.domain.card.ActionTrigger;
import com.semoss.agricola.GamePlay.domain.player.Player;
import com.semoss.agricola.GamePlay.domain.resource.ResourceStruct;
import lombok.Builder;

import java.util.List;

/**
 * 플레이어 행동에 따라 추가 자원을 얻는 보조설비 카드
 */
public class ActionMinorCard extends DefaultMinorCard implements ActionTrigger {
    private final ActionName actionName;
    private final ResourceStruct[] bonusResources;

    @Builder
    ActionMinorCard(Long cardID, int bonusPoint, ResourceStruct[] ingredients, CardType preconditionCardType, int minCardNum, String name, String description, ActionName actionName, ResourceStruct[] bonusResources) {
        super(cardID, bonusPoint, ingredients, preconditionCardType, minCardNum, name, description);
        this.actionName = actionName;
        this.bonusResources = bonusResources;
    }

    @Override
    public void actionTrigger(Player player, History history) {
        if (actionName == history.getEventName()) {
            player.addResource(List.of(bonusResources));
        }
    }
}
