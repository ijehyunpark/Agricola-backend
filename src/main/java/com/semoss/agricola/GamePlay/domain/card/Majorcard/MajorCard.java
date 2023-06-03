package com.semoss.agricola.GamePlay.domain.card.Majorcard;

import com.semoss.agricola.GamePlay.domain.card.Card;
import com.semoss.agricola.GamePlay.domain.player.Player;

/**
 * 주설비 카드
 */
public interface MajorCard extends Card {
    boolean checkPrerequisites(Player player);
    int getBonusPoint();
    void place(Player player);
}
