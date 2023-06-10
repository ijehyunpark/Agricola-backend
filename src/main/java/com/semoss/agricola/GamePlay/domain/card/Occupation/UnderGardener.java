package com.semoss.agricola.GamePlay.domain.card.Occupation;

import com.semoss.agricola.GamePlay.domain.History;
import com.semoss.agricola.GamePlay.domain.card.ActionTrigger;
import com.semoss.agricola.GamePlay.domain.player.Player;
import com.semoss.agricola.GamePlay.domain.resource.ResourceType;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 특정 행동시 추가 자원 획득
 */
@Getter
@Component
public class UnderGardener extends DefaultOccupation implements ActionTrigger {
    private final int playerRequirement;

    public UnderGardener(@Value("${underGardener.id}") Long cardID,
                     @Value("${underGardener.name}") String name,
                     @Value("${underGardener.players}") Integer playerRequirement,
                     @Value("${underGardener.description}") String description) {
        super(cardID, name, description);
        this.playerRequirement = playerRequirement;
    }

    @Override
    public void actionTrigger(Player player, History history) {
        // 날품팔이 행동칸을 이용할 때마다 채소 하나를 얻는다.
        if(history.getEventName().getId() == 6){
            player.addResource(ResourceType.VEGETABLE, 1);
        }
    }
}
