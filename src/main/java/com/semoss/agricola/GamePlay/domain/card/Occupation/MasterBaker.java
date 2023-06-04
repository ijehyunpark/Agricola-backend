package com.semoss.agricola.GamePlay.domain.card.Occupation;

import com.semoss.agricola.GamePlay.domain.History;
import com.semoss.agricola.GamePlay.domain.action.component.ActionType;
import com.semoss.agricola.GamePlay.domain.player.Player;
import com.semoss.agricola.GamePlay.domain.resource.ResourceType;
import com.semoss.agricola.util.Pair;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Getter
@Component
@Scope("prototype")
public class MasterBaker extends DefaultOccupation implements ActionTrigger, ActionCrossTrigger {
    private Long id;
    private String name;
    private int playerRequirement;
    private String description;

    public MasterBaker(@Value("${masterBaker.id}") Long id,
                     @Value("${masterBaker.name}") String name,
                     @Value("${masterBaker.players}") Integer playerRequirement,
                     @Value("${masterBaker.description}") String description) {
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
        // 빵굽기 횟수만큼 곡식 추가
        int bakeCount = history.getActionTypesAndTimes().stream()
                .filter(actionTypeIntegerPair -> actionTypeIntegerPair.first() == ActionType.BAKE)
                .mapToInt(Pair::second)
                .sum();
        player.addResource(ResourceType.GRAIN, bakeCount);
    }

    @Override
    public void actionCrossTrigger(Player player, History history) {
        // TODO: 다른 사람 빵굽기 시 즉시 빵굽기 가능
        throw new RuntimeException("미구현");
    }

    @Override
    public void place(Player player) {
        setOwner(player);
        player.addOccupations(this);
    }
}
