package com.semoss.agricola.GamePlay.domain.card.Minorcard;

import com.semoss.agricola.GamePlay.domain.card.CardType;
import com.semoss.agricola.GamePlay.domain.player.Player;
import com.semoss.agricola.GamePlay.domain.resource.ResourceStruct;
import lombok.Builder;
import lombok.Getter;

@Getter
public class MoveLeftMinorCard extends DefaultMinorCard implements PlaceGetResourceTrigger{
    private final ResourceStruct bonusResource;

    @Builder
    public MoveLeftMinorCard(Long cardID, int bonusPoint, ResourceStruct[] ingredients, CardType preconditionCardType, int minCardNum, ResourceStruct bonusResource) {
        super(cardID, bonusPoint, ingredients, preconditionCardType, minCardNum);
        this.bonusResource = bonusResource;
    }

    @Override
    public void place(Player player) {
        super.place(player);
        player.addResource(bonusResource);
    }
}
