package com.semoss.agricola.GamePlay.domain.card.Occupation;

import com.semoss.agricola.GamePlay.domain.History;
import com.semoss.agricola.GamePlay.domain.card.CardType;
import com.semoss.agricola.GamePlay.domain.player.Player;

public interface Occupation {
    CardType getCardType();
    Player getOwner();
    void setOwner(Player player);
    int getPlayerRequirement();
    Long getId();
    String getName();
    String getDescription();
    void place(Player player);
}
