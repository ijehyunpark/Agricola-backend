package com.semoss.agricola.GamePlay.domain.card.Occupation;

import com.semoss.agricola.GamePlay.domain.History;
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
public class LandAgent extends DefaultOccupation implements ActionTrigger{
    private Long id;
    private String name;
    private int playerRequirement;
    private String description;

    public LandAgent(@Value("${landAgent.id}") Long id,
                     @Value("${landAgent.name}") String name,
                     @Value("${landAgent.players}") Integer playerRequirement,
                     @Value("${landAgent.description}") String description) {
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
    public void actionTrigger(Player player, History history){
        // 곡식 가져오기일 경우에만 실행
        if (history.getEventName().getId() != 3)
            return;

        player.addResource(ResourceStruct.builder()
                .resource(ResourceType.GRAIN)
                .count(1)
                .build());
    }

    @Override
    public void place(Player player) {
        super.place(player);
        player.addResource(ResourceStruct.builder()
                .resource(ResourceType.VEGETABLE)
                .count(1)
                .build());
    }
}
