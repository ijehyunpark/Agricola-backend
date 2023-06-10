package com.semoss.agricola.GamePlay.domain.card;

import com.semoss.agricola.GamePlay.domain.resource.ResourceStruct;
import org.junit.jupiter.api.Disabled;

/**
 * 수확시기에 한 번 음식으로 교환
 */
@Disabled
public interface CookingHarvestTrigger extends Card {
    ResourceStruct getResourceToFoodHarvest();
}
