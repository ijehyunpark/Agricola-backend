package com.semoss.agricola.GamePlay.domain.card.Majorcard;

import com.semoss.agricola.GamePlay.domain.resource.AnimalStruct;
import com.semoss.agricola.GamePlay.domain.resource.ResourceStruct;
import lombok.Builder;
import lombok.Getter;

// 화덕, 화로
@Getter
public class FirePlace extends DefaultMajorCard implements CookingAnytimeTrigger,BakeTrigger{
    private final ResourceStruct[] resourcesToFoodAnytime; // 언제든지 음식으로 교환
    private final AnimalStruct[] animalsToFoodAnytime; // 언제든지 음식으로 교환
    private final int bakeEfficiency; // 빵굽기 효율 == 곡물 하나당 음식 개수

    @Builder
    public FirePlace(Long cardID, int bonusPoint, ResourceStruct[] ingredients, ResourceStruct[] resourcesToFoodAnytime, AnimalStruct[] animalsToFoodAnytime, int bakeEfficiency) {
        super(cardID, bonusPoint, ingredients);
        this.resourcesToFoodAnytime = resourcesToFoodAnytime;
        this.animalsToFoodAnytime = animalsToFoodAnytime;
        this.bakeEfficiency = bakeEfficiency;
    }
}
