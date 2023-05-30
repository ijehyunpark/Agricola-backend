package com.semoss.agricola.GamePlay.domain.card.Minorcard;

import com.semoss.agricola.GamePlay.domain.card.CardType;
import com.semoss.agricola.GamePlay.domain.card.Majorcard.CookingHarvestTrigger;
import com.semoss.agricola.GamePlay.domain.resource.ResourceStruct;
import lombok.Builder;
import lombok.Getter;

@Getter
public class CookingHarvestMinorCard extends DefaultMinorCard implements CookingHarvestTrigger {
    private final ResourceStruct resourceToFoodHarvest;

    @Builder
    CookingHarvestMinorCard(Long cardID, int bonusPoint, ResourceStruct[] ingredients, CardType preconditionCardType, int minCardNum, ResourceStruct resourceToFoodHarvest) {
        super(cardID, bonusPoint, ingredients, preconditionCardType, minCardNum);
        this.resourceToFoodHarvest = resourceToFoodHarvest;
    }
}
