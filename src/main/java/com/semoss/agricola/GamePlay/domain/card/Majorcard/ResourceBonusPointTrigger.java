package com.semoss.agricola.GamePlay.domain.card.Majorcard;

import com.semoss.agricola.GamePlay.domain.player.Player;

/**
 * 자원 수에 따라 추가 점수
 */
public interface ResourceBonusPointTrigger extends MajorCard{
    int checkPoint(Player player);
}
