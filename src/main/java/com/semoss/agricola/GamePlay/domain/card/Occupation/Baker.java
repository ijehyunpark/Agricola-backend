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
public class Baker extends DefaultOccupation implements HarvestTrigger {
    private Long id;
    private String name;
    private int playerRequirement;
    private String description;

    public Baker(@Value("${baker.id}") Long id,
                       @Value("${baker.name}") String name,
                       @Value("${baker.players}") Integer playerRequirement,
                       @Value("${baker.description}") String description) {
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
    public void harvestTrigger(Player player) {
        // TODO : 빵굽기
        throw new RuntimeException("미구현");
    }

    @Override
    public void place(Player player) {
        super.place(player);
        // TODO : 빵굽기
        throw new RuntimeException("미구현");
    }
}
