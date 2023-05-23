package com.semoss.agricola.GamePlay.domain.card;

import com.semoss.agricola.GamePlay.domain.resource.AnimalStruct;
import com.semoss.agricola.GamePlay.domain.player.Player;
import com.semoss.agricola.GamePlay.domain.resource.ResourceStruct;

public interface Card {
    /**
     * 필요 재료 포함
     * @param player
     * @return
     */
    boolean checkPrerequisites(Player player);

    ResourceStruct getResourceToFoodHarvest(); // 수확시 1회에 한해 자원을 음식으로 교환
    ResourceStruct[] getResourcesToFoodAnytime(); // 언제든지 음식으로 교환
    AnimalStruct[] getAnimalsToFoodAnytime(); // 언제든지 음식으로 교환

    CardType getCardType();
    Long getOwner();
    void setOwner(Long userID);
    void place(Player player);
}
