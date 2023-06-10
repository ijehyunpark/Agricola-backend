package com.semoss.agricola.GamePlay.domain.card.Occupation;

import com.semoss.agricola.GamePlay.domain.card.CookingHarvestTrigger;
import com.semoss.agricola.GamePlay.domain.resource.ResourceStruct;
import com.semoss.agricola.GamePlay.domain.resource.ResourceType;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public class MasterBrewer extends DefaultOccupation implements CookingHarvestTrigger {
    private final int playerRequirement;
    private final ResourceStruct resourceToFoodHarvest = ResourceStruct.builder().resource(ResourceType.GRAIN).count(3).build();

    public MasterBrewer(@Value("${masterBrewer.id}") Long cardID,
                     @Value("${masterBrewer.name}") String name,
                     @Value("${masterBrewer.players}") Integer playerRequirement,
                     @Value("${masterBrewer.description}") String description) {
        super(cardID, name, description);
        this.playerRequirement = playerRequirement;
    }

}
