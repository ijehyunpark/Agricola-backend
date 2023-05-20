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
public class Mendicant extends DefaultOccupation implements FinishTrigger {
    private Long id;
    private String name;
    private int playerRequirement;
    private String description;

    public Mendicant(@Value("${landAgent.id}") Long id,
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

    public void finishTrigger(Player player) {
        // 구걸 카드 최대 2장 버림
        int drop = Math.max(2, player.getResource(ResourceType.BEGGING));
        player.useResource(ResourceType.BEGGING, drop);
    }

    @Override
    public void place(Player player) {
        setOwner(player);
        player.addOccupations(this);
    }
}
