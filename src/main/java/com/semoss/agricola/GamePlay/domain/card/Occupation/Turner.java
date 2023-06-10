package com.semoss.agricola.GamePlay.domain.card.Occupation;

import com.semoss.agricola.GamePlay.domain.card.CookingAnytimeTrigger;
import com.semoss.agricola.GamePlay.domain.resource.ResourceStruct;
import com.semoss.agricola.GamePlay.domain.resource.ResourceStructInterface;
import com.semoss.agricola.GamePlay.domain.resource.ResourceType;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 언제든 자원을 음식으로 교환
 */
@Getter
@Component
public class Turner extends DefaultOccupation implements CookingAnytimeTrigger {
    private final int playerRequirement;
    private final List<ResourceStructInterface> resourcesToFoodAnytime = new ArrayList<>(); // 언제든지 음식으로 교환

    public Turner(@Value("${turner.id}") Long cardID,
                     @Value("${turner.name}") String name,
                     @Value("${turner.players}") Integer playerRequirement,
                     @Value("${turner.description}") String description) {
        super(cardID, name, description);
        this.resourcesToFoodAnytime.addAll(List.of(new ResourceStruct[]{ResourceStruct.builder().resource(ResourceType.WOOD).count(1).build()}));
        this.playerRequirement = playerRequirement;
    }
}
