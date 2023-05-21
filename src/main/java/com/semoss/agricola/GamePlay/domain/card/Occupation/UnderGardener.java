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
public class UnderGardener extends DefaultOccupation implements ActionTrigger {
    private Long id;
    private String name;
    private int playerRequirement;
    private String description;

    public UnderGardener(@Value("${underGardener.id}") Long id,
                     @Value("${underGardener.name}") String name,
                     @Value("${underGardener.players}") Integer playerRequirement,
                     @Value("${underGardener.description}") String description) {
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
        // 날품팔이 행동칸을 이용할 때마다 채소 하나를 얻는다.
        if(history.getEventName().getId() == 6){
            player.addResource(ResourceType.VEGETABLE, 1);
        }
    }
}
