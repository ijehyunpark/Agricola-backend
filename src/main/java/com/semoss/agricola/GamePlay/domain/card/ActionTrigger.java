package com.semoss.agricola.GamePlay.domain.card;

import com.semoss.agricola.GamePlay.domain.History;
import com.semoss.agricola.GamePlay.domain.card.Card;
import com.semoss.agricola.GamePlay.domain.player.Player;

/**
 * 행동시 발동
 */
public interface ActionTrigger extends Card {
    void actionTrigger(Player player, History history);
}
