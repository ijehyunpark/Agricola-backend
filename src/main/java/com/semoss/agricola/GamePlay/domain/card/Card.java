package com.semoss.agricola.GamePlay.domain.card;

import com.semoss.agricola.GamePlay.domain.player.Player;

public interface Card {
    boolean checkPrerequisites(Player player, CardDictionary cardDictionary);
    CardType getCardType();
    int getBonusPoint();
    Long getCardID();
    void place(Player player, CardDictionary cardDictionary);
    String getName();
    String getDescription();
}
