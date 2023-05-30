package com.semoss.agricola.GamePlay.domain.card.Minorcard;

import com.semoss.agricola.GamePlay.domain.History;
import com.semoss.agricola.GamePlay.domain.action.implement.ActionName;
import com.semoss.agricola.GamePlay.domain.card.CardType;
import com.semoss.agricola.GamePlay.domain.player.Player;
import com.semoss.agricola.GamePlay.domain.resource.ResourceStruct;
import lombok.Builder;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;

@Getter
public class ActionNameMinorCard extends DefaultMinorCard implements ActionNameTrigger{
    private final ActionName[] actionNames;
    private final ResourceStruct[] bonusResources;

    @Builder
    ActionNameMinorCard(Long cardID, int bonusPoint, ResourceStruct[] ingredients, CardType preconditionCardType, int minCardNum, ActionName[] actionNames, ResourceStruct[] bonusResources) {
        super(cardID, bonusPoint, ingredients, preconditionCardType, minCardNum);
        this.actionNames = actionNames;
        this.bonusResources = bonusResources;
    }

    @Override
    public void actionNameTrigger(Player player, History history) {
        if (Arrays.stream(actionNames).anyMatch(actionName -> actionName == history.getEventName())) {
            player.addResource(List.of(bonusResources));
        }
    }
}
