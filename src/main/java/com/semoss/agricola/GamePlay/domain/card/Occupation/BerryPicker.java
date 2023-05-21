package com.semoss.agricola.GamePlay.domain.card.Occupation;

import com.semoss.agricola.GamePlay.domain.History;
import com.semoss.agricola.GamePlay.domain.player.Player;
import com.semoss.agricola.GamePlay.domain.resource.ResourceType;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Getter
@Component
@Scope("prototype")
public class BerryPicker extends DefaultOccupation implements ActionTrigger {
    private Long id;
    private String name;
    private int playerRequirement;
    private String description;

    public BerryPicker(@Value("${berryPicker.id}") Long id,
                     @Value("${berryPicker.name}") String name,
                     @Value("${berryPicker.players}") Integer playerRequirement,
                     @Value("${berryPicker.description}") String description) {
        this.id = id;
        this.name = name;
        this.playerRequirement = playerRequirement;
        this.description = description;
    }

    @Override
    public Player getOwner() {
        return super.getOwner();
    }

    @Override
    public void setOwner(Player player) {
        super.setOwner(player);
    }

    @Override
    public void actionTrigger(Player player, History history) {
        boolean isGetWood = history.getChanges().stream()
                .anyMatch(resourceStruct -> resourceStruct.getResource() == ResourceType.WOOD && resourceStruct.getCount() > 0);
        if(!isGetWood)
            return;

        player.addResource(ResourceType.FOOD, 1);
    }
}
