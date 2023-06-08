package com.semoss.agricola.GamePlay.domain.card.Minorcard;

import com.semoss.agricola.GamePlay.domain.card.CardDictionary;
import com.semoss.agricola.GamePlay.domain.card.CardType;
import com.semoss.agricola.GamePlay.domain.player.Player;
import com.semoss.agricola.GamePlay.domain.resource.ResourceStruct;
import lombok.Builder;
import lombok.Getter;

/**
 * 카드를 둘때 자원을 획득하는 보조설비 카드입니다. TODO 현재 버전에서는 왼쪽 사람에게 카드를 넘기는 기능이 미구현되어있습니다.
 */
@Getter
public class MoveLeftMinorCard extends DefaultMinorCard implements PlaceGetResourceTrigger{
    private final ResourceStruct bonusResource;

    @Builder
    public MoveLeftMinorCard(Long cardID, int bonusPoint, ResourceStruct[] ingredients, CardType preconditionCardType, int minCardNum, String name, String description, ResourceStruct bonusResource) {
        super(cardID, bonusPoint, ingredients, preconditionCardType, minCardNum, name, description);
        this.bonusResource = bonusResource;
    }

    @Override
    public void place(Player player, CardDictionary cardDictionary, int round) {
        super.place(player, cardDictionary, round);
        player.addResource(bonusResource);
    }
}
