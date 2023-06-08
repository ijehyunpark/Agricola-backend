package com.semoss.agricola.GamePlay.domain.card.Occupation;

import com.semoss.agricola.GamePlay.domain.player.Player;
import com.semoss.agricola.GamePlay.domain.resource.ResourceType;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 게임 종료시 행동 (구걸 카드 2장 버림)
 */
@Getter
@Component
public class Mendicant extends DefaultOccupation implements FinishTrigger {
    private final int playerRequirement;

    public Mendicant(@Value("${mendicant.id}") Long cardID,
                     @Value("${mendicant.name}") String name,
                     @Value("${mendicant.players}") Integer playerRequirement,
                     @Value("${mendicant.description}") String description) {
        super(cardID, name, description);
        this.playerRequirement = playerRequirement;
    }

    @Override
    public void finishTrigger(Player player) {
        // 구걸 카드 최대 2장 버림
        int drop = Math.max(2, player.getResource(ResourceType.BEGGING));
        player.useResource(ResourceType.BEGGING, drop);
    }
}
