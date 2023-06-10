package com.semoss.agricola.GamePlay.domain.card.Minorcard;

import com.semoss.agricola.GamePlay.domain.card.CardType;
import com.semoss.agricola.GamePlay.domain.card.CookingHarvestTrigger;
import com.semoss.agricola.GamePlay.domain.resource.ResourceStruct;
import lombok.Builder;
import lombok.Getter;

/**
 * 수확시 자원을 음식으로 교환할 수 있는 보조설비 카드
 */
@Getter
public class CookingHarvestMinorCard extends DefaultMinorCard implements CookingHarvestTrigger {
    private final ResourceStruct resourceToFoodHarvest;

    @Builder
    CookingHarvestMinorCard(Long cardID, int bonusPoint, ResourceStruct[] ingredients, CardType preconditionCardType, int minCardNum, String name, String description, ResourceStruct resourceToFoodHarvest) {
        super(cardID, bonusPoint, ingredients, preconditionCardType, minCardNum, name, description);
        this.resourceToFoodHarvest = resourceToFoodHarvest;
    }
}
