package com.semoss.agricola.GamePlay.domain.card.Minorcard;

import com.semoss.agricola.GamePlay.domain.card.CardType;
import com.semoss.agricola.GamePlay.domain.player.FieldType;
import com.semoss.agricola.GamePlay.domain.player.Player;
import com.semoss.agricola.GamePlay.domain.resource.ResourceStruct;
import lombok.Builder;
import lombok.Getter;

/**
 * 필드 타입의 개수에 따라 게임 종료시 추가 점수르 획득하는 보조설비 카드입니다.
 */
@Getter
public class FieldBonusMinorCard extends DefaultMinorCard implements FieldBonusPoint{
    private final FieldType bonusFieldType;
    private final int[] fieldBonusPoint;

    @Builder
    FieldBonusMinorCard(Long cardID, int bonusPoint, ResourceStruct[] ingredients, CardType preconditionCardType, int minCardNum, String name, String description, FieldType bonusFieldType, int[] fieldBonusPoint) {
        super(cardID, bonusPoint, ingredients, preconditionCardType, minCardNum, name, description);
        this.bonusFieldType = bonusFieldType;
        this.fieldBonusPoint = fieldBonusPoint;
    }

    @Override
    public int checkPoint(Player player) {
        return fieldBonusPoint[Integer.min(player.getNumField(bonusFieldType),fieldBonusPoint.length-1)];
    }
}
