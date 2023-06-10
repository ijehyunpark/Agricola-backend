package com.semoss.agricola.GamePlay.domain.card.Occupation;

import com.semoss.agricola.GamePlay.domain.History;
import com.semoss.agricola.GamePlay.domain.card.ActionTrigger;
import com.semoss.agricola.GamePlay.domain.player.Player;
import com.semoss.agricola.GamePlay.domain.resource.ResourceStruct;
import com.semoss.agricola.GamePlay.domain.resource.ResourceType;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 특정 행동시 자원 추가 획득
 */
@Getter
@Component
public class Greengrocer extends DefaultOccupation implements ActionTrigger {
    private final int playerRequirement;

    public Greengrocer(@Value("${greengrocer.id}") Long cardID,
                     @Value("${greengrocer.name}") String name,
                     @Value("${greengrocer.players}") Integer playerRequirement,
                     @Value("${greengrocer.description}") String description) {
        super(cardID, name, description);
        this.playerRequirement = playerRequirement;
    }

    @Override
    public void actionTrigger(Player player, History history){
        // 곡식 가져오기일 경우에만 실행
        if (history.getEventName().getId() != 3)
            return;

        player.addResource(ResourceStruct.builder()
                .resource(ResourceType.VEGETABLE)
                .count(1)
                .build());
    }
}
