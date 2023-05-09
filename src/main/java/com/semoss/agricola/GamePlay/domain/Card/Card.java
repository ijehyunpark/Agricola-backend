package com.semoss.agricola.GamePlay.domain.Card;

import com.semoss.agricola.GamePlay.domain.Player;

public interface Card {
    /**
     * 필요 재료 포함
     * @param player
     * @return
     */
    boolean checkPrerequisites(Player player);
    CardType getCardType();
    String getOwner();
    void setOwner(String userID);
    void useResource(Player player);
}
