package com.semoss.agricola.GamePlay.domain.card.Minorcard;

import com.semoss.agricola.GamePlay.domain.card.CardType;
import com.semoss.agricola.GamePlay.domain.card.CookingAnytimeTrigger;
import com.semoss.agricola.GamePlay.domain.resource.AnimalStruct;
import com.semoss.agricola.GamePlay.domain.resource.ResourceStruct;
import lombok.Builder;
import lombok.Getter;

/**
 * 언제든 음식으로 교환 가능한 보조설비 카드입니다.
 */
@Getter
public class Windmill extends DefaultMinorCard implements CookingAnytimeTrigger {
    private final ResourceStruct[] resourcesToFoodAnytime; // 언제든지 음식으로 교환
    private final AnimalStruct[] animalsToFoodAnytime; // 언제든지 음식으로 교환

    @Builder
    Windmill(Long cardID, int bonusPoint, ResourceStruct[] ingredients, CardType preconditionCardType, int minCardNum, String name, String description, ResourceStruct[] resourcesToFoodAnytime, AnimalStruct[] animalsToFoodAnytime) {
        super(cardID, bonusPoint, ingredients, preconditionCardType, minCardNum, name, description);
        this.resourcesToFoodAnytime = resourcesToFoodAnytime;
        this.animalsToFoodAnytime = animalsToFoodAnytime;
    }
}
