package com.semoss.agricola.GamePlay.domain.action.component;

import com.semoss.agricola.GamePlay.domain.player.Player;
import com.semoss.agricola.GamePlay.domain.resource.AnimalStruct;
import com.semoss.agricola.GamePlay.domain.resource.ResourceStruct;
import com.semoss.agricola.GamePlay.domain.resource.ResourceStructInterface;

import java.util.List;

public interface StackAction extends Action {
    default void runStackAction(Player player, List<ResourceStructInterface> stacks) {
        stacks.stream()
                .filter(ResourceStructInterface::isResource)
                .map(resourceStructInterface -> (ResourceStruct) resourceStructInterface)
                .forEach(player::addResource);

        stacks.stream()
                .filter(ResourceStructInterface::isAnimal)
                .map(resourceStructInterface -> (AnimalStruct) resourceStructInterface)
                .forEach(player::addAnimal);
    }
}
