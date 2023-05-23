package com.semoss.agricola.GamePlay.domain.card.Occupation;

import com.semoss.agricola.GamePlay.domain.History;
import com.semoss.agricola.GamePlay.domain.player.Player;

/**
 * 행동시 발동
 */
public interface ActionTrigger {
    void actionTrigger(Player player, History history);
}
