package com.semoss.agricola.GamePlay.domain.card.Majorcard;

import com.semoss.agricola.GamePlay.domain.card.Card;
import com.semoss.agricola.GamePlay.domain.resource.AnimalStruct;
import com.semoss.agricola.GamePlay.domain.resource.ResourceStruct;

/**
 * 언제든 음식으로 교환
 */
public interface CookingAnytimeTrigger extends Card {
    ResourceStruct[] getResourcesToFoodAnytime(); // 언제든지 음식으로 교환
    AnimalStruct[] getAnimalsToFoodAnytime(); // 언제든지 음식으로 교환
}
