package com.semoss.agricola.GamePlay.domain.card.Minorcard;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.semoss.agricola.GamePlay.domain.player.FieldType;
import com.semoss.agricola.GamePlay.domain.player.Player;

/**
 * 필드 유형에 따라 추가 점수를 획득
 */
public interface FieldBonusPoint extends MinorCard{
    @JsonIgnore
    FieldType getBonusFieldType();
    @JsonIgnore
    int[] getFieldBonusPoint();
    int checkPoint(Player player);
}
