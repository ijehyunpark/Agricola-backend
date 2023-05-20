package com.semoss.agricola.GamePlay.domain.card.Occupation;

import com.semoss.agricola.GamePlay.domain.player.Player;
import com.semoss.agricola.GamePlay.domain.resource.ResourceStruct;
import com.semoss.agricola.GamePlay.domain.resource.ResourceType;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Getter
@Component
@Scope("prototype")
public class LandAgent extends DefaultOccupation {
    private Long id = 100L;
    private String name;
    private int playerRequirement = 3;
    private String description;

    public LandAgent(@Value("${landAgent.name}") String name,
                     @Value("${landAgent.description}") String description) {
        this.name = name;
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

    public void actionTrigger(Player player){
        player.addResource(ResourceStruct.builder()
                .resource(ResourceType.GRAIN)
                .count(1)
                .build());
    }

    @Override
    public void place(Player player) {
        setOwner(player);
        player.addResource(ResourceStruct.builder()
                .resource(ResourceType.VEGETABLE)
                .count(1)
                .build());
    }
}
