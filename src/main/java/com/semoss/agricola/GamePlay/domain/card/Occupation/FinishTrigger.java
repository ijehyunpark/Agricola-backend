package com.semoss.agricola.GamePlay.domain.card.Occupation;

import com.semoss.agricola.GamePlay.domain.player.Player;

public interface FinishTrigger extends Occupation {
    void finishTrigger(Player player);
}
