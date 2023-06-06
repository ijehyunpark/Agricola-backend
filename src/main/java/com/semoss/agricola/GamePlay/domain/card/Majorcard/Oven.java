package com.semoss.agricola.GamePlay.domain.card.Majorcard;

import com.semoss.agricola.GamePlay.domain.card.BakeTrigger;
import com.semoss.agricola.GamePlay.domain.resource.ResourceStruct;
import lombok.Builder;
import lombok.Getter;

/**
 * 흙가마, 돌가마
 */
@Getter
public class Oven extends DefaultMajorCard implements BakeTrigger {
    private final int bakeEfficiency; // 빵굽기 효율 == 곡물 하나당 음식 개수; // 수확시 1회에 한해 자원을 음식으로 교환

    @Builder
    public Oven(Long cardID, int bonusPoint, ResourceStruct[] ingredients, String name, String description, int bakeEfficiency) {
        super(cardID, bonusPoint, ingredients, name, description);
        this.bakeEfficiency = bakeEfficiency;
    }

}
