package com.semoss.agricola.GamePlay.domain.card.Minorcard;

import com.semoss.agricola.GamePlay.domain.card.CardType;
import com.semoss.agricola.GamePlay.domain.player.FieldType;
import com.semoss.agricola.GamePlay.domain.player.Player;
import com.semoss.agricola.GamePlay.domain.resource.ResourceStruct;
import lombok.Builder;
import lombok.Getter;

@Getter
public class FieldBonusMinorCard extends DefaultMinorCard implements FieldBonusPoint{
    private final FieldType bonusFieldType;
    private final int[] fieldBonusPoint;

    @Builder
    FieldBonusMinorCard(Long cardID, int bonusPoint, ResourceStruct[] ingredients, CardType preconditionCardType, int minCardNum, FieldType bonusFieldType, int[] fieldBonusPoint) {
        super(cardID, bonusPoint, ingredients, preconditionCardType, minCardNum);
        this.bonusFieldType = bonusFieldType;
        this.fieldBonusPoint = fieldBonusPoint;
    }

    @Override
    public int checkPoint(Player player) {
        return fieldBonusPoint[Integer.min(player.getNumField(bonusFieldType),fieldBonusPoint.length-1)];
    }
}
