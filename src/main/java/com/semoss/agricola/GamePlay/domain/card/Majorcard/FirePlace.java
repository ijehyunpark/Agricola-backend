package com.semoss.agricola.GamePlay.domain.card.Majorcard;

import com.semoss.agricola.GamePlay.domain.resource.AnimalStruct;
import com.semoss.agricola.GamePlay.domain.resource.ResourceStruct;
import com.semoss.agricola.GamePlay.domain.resource.ResourceStructInterface;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

// 화덕, 화로
@Getter
public class FirePlace extends DefaultMajorCard implements CookingAnytimeTrigger, BakeTrigger {
    private final List<ResourceStructInterface> resourcesToFoodAnytime = new ArrayList<>(); // 언제든지 음식으로 교환
    private final int bakeEfficiency; // 빵굽기 효율 == 곡물 하나당 음식 개수

    @Builder
    public FirePlace(Long cardID, int bonusPoint, ResourceStruct[] ingredients, ResourceStruct[] resourcesToFoodAnytime, AnimalStruct[] animalsToFoodAnytime, int bakeEfficiency) {
        super(cardID, bonusPoint, ingredients);
        this.resourcesToFoodAnytime.addAll(List.of(resourcesToFoodAnytime));
        this.resourcesToFoodAnytime.addAll(List.of(animalsToFoodAnytime));
        this.bakeEfficiency = bakeEfficiency;
    }

    @Override
    public boolean hasBakeMajorTrigger() {
        return true;
    }

    @Override
    public boolean hasCookingAnytimeTrigger() {
        return true;
    }

    @Override
    public boolean hasCookingHarvestTrigger() {
        return false;
    }
}
