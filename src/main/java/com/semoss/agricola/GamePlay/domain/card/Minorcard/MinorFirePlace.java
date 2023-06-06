package com.semoss.agricola.GamePlay.domain.card.Minorcard;

import com.semoss.agricola.GamePlay.domain.card.CardType;
import com.semoss.agricola.GamePlay.domain.card.CookingAnytimeTrigger;
import com.semoss.agricola.GamePlay.domain.card.BakeTrigger;
import com.semoss.agricola.GamePlay.domain.resource.AnimalStruct;
import com.semoss.agricola.GamePlay.domain.resource.ResourceStruct;
import com.semoss.agricola.GamePlay.domain.resource.ResourceStructInterface;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * 화로와 같은 역할을 하는 보조설비 카드입니다 (언제든 음식 교환 + 빵굽기 효율증가)
 */
@Getter
public class MinorFirePlace extends DefaultMinorCard implements CookingAnytimeTrigger, BakeTrigger{
    private final List<ResourceStructInterface> resourcesToFoodAnytime = new ArrayList<>(); // 언제든지 음식으로 교환
    private final int bakeEfficiency; // 빵굽기 효율 == 곡물 하나당 음식 개수

    @Builder
    MinorFirePlace(Long cardID, int bonusPoint, ResourceStruct[] ingredients, CardType preconditionCardType, int minCardNum, String name, String description, ResourceStruct[] resourcesToFoodAnytime, AnimalStruct[] animalsToFoodAnytime, int bakeEfficiency) {
        super(cardID, bonusPoint, ingredients, preconditionCardType, minCardNum, name, description);
        this.resourcesToFoodAnytime.addAll(List.of(resourcesToFoodAnytime));
        this.resourcesToFoodAnytime.addAll(List.of(animalsToFoodAnytime));
        this.bakeEfficiency = bakeEfficiency;
    }

}
