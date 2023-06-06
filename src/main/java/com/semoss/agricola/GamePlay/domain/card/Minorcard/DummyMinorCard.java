package com.semoss.agricola.GamePlay.domain.card.Minorcard;

import com.semoss.agricola.GamePlay.domain.card.CardType;
import com.semoss.agricola.GamePlay.domain.player.Player;
import com.semoss.agricola.GamePlay.domain.resource.ResourceStruct;
import com.semoss.agricola.GamePlay.domain.resource.ResourceType;
import lombok.Builder;
import lombok.Getter;

/**
 * 더미 보조설비 카드 입니다.
 */
@Getter
public class DummyMinorCard extends DefaultMinorCard implements PlaceGetResourceTrigger{
    private final ResourceStruct bonusResource = ResourceStruct.builder().resource(ResourceType.BEGGING).count(1).build();
    private static Long nextID = 1001L;

    @Builder
    protected DummyMinorCard(int bonusPoint, ResourceStruct[] ingredients, CardType preconditionCardType, int minCardNum, String name, String description) {
        super(nextID, bonusPoint, ingredients, preconditionCardType, minCardNum, name, description);
        nextID += 2;
    }


    @Override
    public void place(Player player) {
        super.place(player);
        player.addResource(bonusResource);
    }
}
