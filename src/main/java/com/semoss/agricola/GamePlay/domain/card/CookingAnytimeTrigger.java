package com.semoss.agricola.GamePlay.domain.card;

import com.semoss.agricola.GamePlay.domain.card.Card;
import com.semoss.agricola.GamePlay.domain.resource.ResourceStructInterface;

import java.util.List;

/**
 * 언제든 음식으로 교환
 */
public interface CookingAnytimeTrigger extends Card {
    List<ResourceStructInterface> getResourcesToFoodAnytime(); // 언제든지 음식으로 교환
}
