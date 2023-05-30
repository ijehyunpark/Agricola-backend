package com.semoss.agricola.GamePlay.domain.card.Minorcard;

import com.semoss.agricola.GamePlay.domain.card.Card;
import com.semoss.agricola.GamePlay.domain.player.Player;

public interface MinorCard extends Card {
    boolean checkPrerequisites(Player player);
    int getBonusPoint();
    void place(Player player);
}
