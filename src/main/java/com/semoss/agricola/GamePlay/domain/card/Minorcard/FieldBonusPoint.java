package com.semoss.agricola.GamePlay.domain.card.Minorcard;

import com.semoss.agricola.GamePlay.domain.player.FieldType;
import com.semoss.agricola.GamePlay.domain.player.Player;

public interface FieldBonusPoint extends MinorCard{
    FieldType getBonusFieldType();
    int[] getFieldBonusPoint();
    int checkPoint(Player player);
}
