package com.semoss.agricola.GamePlay.domain.card;

import com.semoss.agricola.GamePlay.domain.player.Player;

public interface Card {
    boolean checkPrerequisites(Player player);
    CardType getCardType();
    int getBonusPoint();
    Long getCardID();
    void place(Player player);
    String getName();
    String getDescription();
}
