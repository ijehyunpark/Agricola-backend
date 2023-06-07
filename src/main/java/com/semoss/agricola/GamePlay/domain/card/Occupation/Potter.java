package com.semoss.agricola.GamePlay.domain.card.Occupation;

import com.semoss.agricola.GamePlay.domain.card.CookingHarvestTrigger;
import com.semoss.agricola.GamePlay.domain.resource.ResourceStruct;
import com.semoss.agricola.GamePlay.domain.resource.ResourceType;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 수확시기에 자원을 음식으로 교환
 */
@Getter
@Component
public class Potter extends DefaultOccupation implements CookingHarvestTrigger {
    private final ResourceStruct resourceToFoodHarvest = ResourceStruct.builder().resource(ResourceType.CLAY).count(2).build();
    private final int playerRequirement;

    public Potter(@Value("${potter.id}") Long cardID,
                         @Value("${potter.name}") String name,
                         @Value("${potter.players}") Integer playerRequirement,
                         @Value("${potter.description}") String description) {
        super(cardID, name, description);
        this.playerRequirement = playerRequirement;
    }
}
