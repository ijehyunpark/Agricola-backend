package com.semoss.agricola.GamePlay.domain.card.Occupation;

import com.semoss.agricola.GamePlay.domain.card.CookingAnytimeTrigger;
import com.semoss.agricola.GamePlay.domain.resource.AnimalStruct;
import com.semoss.agricola.GamePlay.domain.resource.ResourceStruct;
import com.semoss.agricola.GamePlay.domain.resource.ResourceStructInterface;
import com.semoss.agricola.GamePlay.domain.resource.ResourceType;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 언제든 자원을 음식으로 교환
 */
@Getter
@Component
@Scope("prototype")
public class Turner extends DefaultOccupation implements CookingAnytimeTrigger {
    private int playerRequirement;
    private final List<ResourceStructInterface> resourcesToFoodAnytime = new ArrayList<>(); // 언제든지 음식으로 교환

    public Turner(@Value("${mendicant.id}") Long cardID,
                     @Value("${mendicant.name}") String name,
                     @Value("${mendicant.players}") Integer playerRequirement,
                     @Value("${mendicant.description}") String description) {
        super(cardID, name, description);
        this.resourcesToFoodAnytime.addAll(List.of(new ResourceStruct[]{ResourceStruct.builder().resource(ResourceType.WOOD).count(1).build()}));
        this.playerRequirement = playerRequirement;
    }
}
