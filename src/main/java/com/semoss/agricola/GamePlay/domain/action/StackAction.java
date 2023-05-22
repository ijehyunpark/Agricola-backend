package com.semoss.agricola.GamePlay.domain.action;

import com.semoss.agricola.GamePlay.domain.History;
import com.semoss.agricola.GamePlay.domain.player.Player;
import com.semoss.agricola.GamePlay.domain.resource.ResourceStructInterface;

import java.util.List;

public interface StackAction extends Action {
    void addStackResource(Player player, List<ResourceStructInterface> stacks, History history);
}
