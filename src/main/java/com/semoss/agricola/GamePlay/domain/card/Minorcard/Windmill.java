package com.semoss.agricola.GamePlay.domain.card.Minorcard;

import com.semoss.agricola.GamePlay.domain.card.CardType;
import com.semoss.agricola.GamePlay.domain.card.CookingAnytimeTrigger;
import com.semoss.agricola.GamePlay.domain.resource.AnimalStruct;
import com.semoss.agricola.GamePlay.domain.resource.ResourceStruct;
import com.semoss.agricola.GamePlay.domain.resource.ResourceStructInterface;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * 언제든 음식으로 교환 가능한 보조설비 카드입니다.
 */
@Getter
public class Windmill extends DefaultMinorCard implements CookingAnytimeTrigger {
    private final List<ResourceStructInterface> resourcesToFoodAnytime = new ArrayList<>();

    @Builder
    Windmill(Long cardID, int bonusPoint, ResourceStruct[] ingredients, CardType preconditionCardType, int minCardNum, String name, String description, ResourceStruct[] resourcesToFoodAnytime, AnimalStruct[] animalsToFoodAnytime) {
        super(cardID, bonusPoint, ingredients, preconditionCardType, minCardNum, name, description);
        if (resourcesToFoodAnytime != null)
            this.resourcesToFoodAnytime.addAll(List.of(resourcesToFoodAnytime));
        if (animalsToFoodAnytime != null)
            this.resourcesToFoodAnytime.addAll(List.of(animalsToFoodAnytime));
    }
}
