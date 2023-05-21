package com.semoss.agricola.GamePlay.domain.card.Occupation;

import com.semoss.agricola.GamePlay.domain.History;
import com.semoss.agricola.GamePlay.domain.player.Player;

/**
 * 다른 사람 행동시 발동 TODO: trigger 배치
 */
public interface ActionCrossTrigger extends Occupation {
    void actionCrossTrigger(Player player, History history);
}
