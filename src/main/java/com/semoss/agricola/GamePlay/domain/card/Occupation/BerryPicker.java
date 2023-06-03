package com.semoss.agricola.GamePlay.domain.card.Occupation;

import com.semoss.agricola.GamePlay.domain.History;
import com.semoss.agricola.GamePlay.domain.card.ActionTrigger;
import com.semoss.agricola.GamePlay.domain.player.Player;
import com.semoss.agricola.GamePlay.domain.resource.ResourceStruct;
import com.semoss.agricola.GamePlay.domain.resource.ResourceStructInterface;
import com.semoss.agricola.GamePlay.domain.resource.ResourceType;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * 특정 행동시 추가 자원 획득
 */
@Getter
@Component
@Scope("prototype")
public class BerryPicker extends DefaultOccupation implements ActionTrigger {
    private int playerRequirement;

    public BerryPicker(@Value("${berryPicker.id}") Long cardID,
                     @Value("${berryPicker.name}") String name,
                     @Value("${berryPicker.players}") Integer playerRequirement,
                     @Value("${berryPicker.description}") String description) {
        super(cardID, name, description);
        this.playerRequirement = playerRequirement;
    }

    @Override
    public void actionTrigger(Player player, History history) {
        boolean isGetWood = history.getChanges().stream()
                .filter(ResourceStructInterface::isResource)
                .map(resourceStructInterface -> (ResourceStruct) resourceStructInterface)
                .anyMatch(resourceStruct -> resourceStruct.getResource() == ResourceType.WOOD && resourceStruct.getCount() > 0);
        if(!isGetWood)
            return;

        player.addResource(ResourceType.FOOD, 1);
    }
}
