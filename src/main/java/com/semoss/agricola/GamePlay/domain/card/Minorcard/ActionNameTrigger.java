package com.semoss.agricola.GamePlay.domain.card.Minorcard;

import com.semoss.agricola.GamePlay.domain.History;
import com.semoss.agricola.GamePlay.domain.action.implement.ActionName;
import com.semoss.agricola.GamePlay.domain.player.Player;
import com.semoss.agricola.GamePlay.domain.resource.ResourceStruct;

public interface ActionNameTrigger extends MinorCard{
    ActionName[] getActionNames();
    ResourceStruct[] getBonusResources();
    void actionNameTrigger(Player player, History history);
}
