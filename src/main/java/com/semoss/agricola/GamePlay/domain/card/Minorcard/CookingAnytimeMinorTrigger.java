package com.semoss.agricola.GamePlay.domain.card.Minorcard;

import com.semoss.agricola.GamePlay.domain.resource.AnimalStruct;
import com.semoss.agricola.GamePlay.domain.resource.ResourceStruct;
import org.junit.jupiter.api.Disabled;

@Disabled
public interface CookingAnytimeMinorTrigger extends MinorCard{
    ResourceStruct[] getResourcesToFoodAnytime(); // 언제든지 음식으로 교환
    AnimalStruct[] getAnimalsToFoodAnytime(); // 언제든지 음식으로 교환
}
