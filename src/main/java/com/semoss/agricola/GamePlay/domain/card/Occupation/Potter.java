package com.semoss.agricola.GamePlay.domain.card.Occupation;

import com.semoss.agricola.GamePlay.domain.card.CookingHarvestTrigger;
import com.semoss.agricola.GamePlay.domain.resource.ResourceStruct;
import com.semoss.agricola.GamePlay.domain.resource.ResourceType;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * 수확시기에 자원을 음식으로 교환
 */
@Getter
@Component
@Scope("prototype")
public class Potter extends DefaultOccupation implements CookingHarvestTrigger {
    private final ResourceStruct resourceToFoodHarvest = ResourceStruct.builder().resource(ResourceType.CLAY).count(2).build();
    private final int playerRequirement;

    public Potter(@Value("${mendicant.id}") Long cardID,
                         @Value("${mendicant.name}") String name,
                         @Value("${mendicant.players}") Integer playerRequirement,
                         @Value("${mendicant.description}") String description) {
        super(cardID, name, description);
        this.playerRequirement = playerRequirement;
    }
}
