package com.semoss.agricola.GamePlay.domain.card.Majorcard;

import com.semoss.agricola.GamePlay.domain.resource.ResourceStruct;
import lombok.Builder;
import lombok.Getter;

@Getter
public class Oven extends DefaultMajorCard implements BakeTrigger{
    private final int bakeEfficiency; // 빵굽기 효율 == 곡물 하나당 음식 개수; // 수확시 1회에 한해 자원을 음식으로 교환

    @Builder
    public Oven(Long cardID, int bonusPoint, ResourceStruct[] ingredients, int bakeEfficiency) {
        super(cardID, bonusPoint, ingredients);
        this.bakeEfficiency = bakeEfficiency;
    }
}
