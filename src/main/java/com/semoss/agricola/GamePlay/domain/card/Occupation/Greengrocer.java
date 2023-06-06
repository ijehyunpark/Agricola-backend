package com.semoss.agricola.GamePlay.domain.card.Occupation;

import com.semoss.agricola.GamePlay.domain.History;
import com.semoss.agricola.GamePlay.domain.card.ActionTrigger;
import com.semoss.agricola.GamePlay.domain.player.Player;
import com.semoss.agricola.GamePlay.domain.resource.ResourceStruct;
import com.semoss.agricola.GamePlay.domain.resource.ResourceType;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * 특정 행동시 자원 추가 획득
 */
@Getter
@Component
@Scope("prototype")
public class Greengrocer extends DefaultOccupation implements ActionTrigger {
    private int playerRequirement;

    public Greengrocer(@Value("${landAgent.id}") Long cardID,
                     @Value("${landAgent.name}") String name,
                     @Value("${landAgent.players}") Integer playerRequirement,
                     @Value("${landAgent.description}") String description) {
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