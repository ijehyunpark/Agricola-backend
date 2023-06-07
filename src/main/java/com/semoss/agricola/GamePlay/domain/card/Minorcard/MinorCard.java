package com.semoss.agricola.GamePlay.domain.card.Minorcard;

import com.semoss.agricola.GamePlay.domain.card.Card;
import com.semoss.agricola.GamePlay.domain.card.CardDictionary;
import com.semoss.agricola.GamePlay.domain.player.Player;

public interface MinorCard extends Card {
    boolean checkPrerequisites(Player player, CardDictionary cardDictionary);
    int getBonusPoint();
    void place(Player player, CardDictionary cardDictionary);
}