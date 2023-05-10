package com.semoss.agricola.GamePlay.domain.card;

import com.semoss.agricola.GamePlay.domain.player.Player;

public interface Card {
    /**
     * 필요 재료 포함
     * @param player
     * @return
     */
    boolean checkPrerequisites(Player player);
    CardType getCardType();
    Long getOwner();
    void setOwner(Long userID);
    void useResource(Player player);
}
