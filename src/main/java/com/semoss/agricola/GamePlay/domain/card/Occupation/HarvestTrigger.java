package com.semoss.agricola.GamePlay.domain.card.Occupation;

import com.semoss.agricola.GamePlay.domain.player.Player;

/**
 * 수확 단계에 실행
 */
public interface HarvestTrigger extends Occupation {
    void harvestTrigger(Player player);
}
