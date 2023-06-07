package com.semoss.agricola.GamePlay.domain.card.Occupation;

import com.semoss.agricola.GamePlay.domain.card.CardDictionary;
import com.semoss.agricola.GamePlay.domain.player.Player;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * 미구현입니다. 안쓸거에요. 지워도 될까요?
 */
@Getter
@Component
@Scope("prototype")
public class Baker extends DefaultOccupation implements HarvestTrigger {
    private int playerRequirement;

    public Baker(@Value("${baker.id}") Long cardID,
                       @Value("${baker.name}") String name,
                       @Value("${baker.players}") Integer playerRequirement,
                       @Value("${baker.description}") String description) {
        super(cardID, name, description);
        this.playerRequirement = playerRequirement;
    }

    @Override
    public void harvestTrigger(Player player) {
        // TODO : 빵굽기
        throw new RuntimeException("미구현");
    }

    @Override
    public void place(Player player, CardDictionary cardDictionary, int round) {
        super.place(player, cardDictionary, round);
        // TODO : 빵굽기
        throw new RuntimeException("미구현");
    }
}
