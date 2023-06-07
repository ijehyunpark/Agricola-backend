package com.semoss.agricola.GamePlay.domain.card.Occupation;

import com.semoss.agricola.GamePlay.domain.History;
import com.semoss.agricola.GamePlay.domain.action.component.ActionType;
import com.semoss.agricola.GamePlay.domain.player.Player;
import com.semoss.agricola.GamePlay.domain.resource.ResourceType;
import com.semoss.agricola.util.Pair;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 다른 사람이 특정 행동을 할 때 추가 자원을 획득합니다.
 */
@Getter
@Component
public class BreadSeller extends DefaultOccupation implements ActionCrossTrigger{
    private final int playerRequirement;

    public BreadSeller(@Value("${breadSeller.id}") Long cardID,
                  @Value("${breadSeller.name}") String name,
                  @Value("${breadSeller.players}") Integer playerRequirement,
                  @Value("${breadSeller.description}") String description) {
        super(cardID, name, description);
        this.playerRequirement = playerRequirement;
    }

    @Override
    public void actionCrossTrigger(Player player, History history) {
        int sum = history.getActionTypesAndTimes().stream()
                        .filter(pair -> pair.first() == ActionType.BAKE)
                                .mapToInt(Pair::second).sum();
        player.addResource(ResourceType.GRAIN, sum);
    }
}
