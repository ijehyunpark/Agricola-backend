package com.semoss.agricola.GamePlay.domain.card.Occupation;

import com.semoss.agricola.GamePlay.domain.player.Player;

/**
 * 게임 종료시 발동
 */
public interface FinishTrigger extends Occupation {
    void finishTrigger(Player player);
}
