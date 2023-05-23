package com.semoss.agricola.GamePlay.domain.action;

import com.semoss.agricola.GamePlay.domain.player.Player;
import com.semoss.agricola.GamePlay.domain.resource.AnimalStruct;
import com.semoss.agricola.GamePlay.domain.resource.ResourceStruct;
import com.semoss.agricola.GamePlay.domain.resource.ResourceStructInterface;

import java.util.List;

public interface StackAction extends Action {
    default void runstackAction(Player player, List<ResourceStructInterface> resourceStructInterfaces) {
        resourceStructInterfaces.stream()
                .filter(ResourceStructInterface::isResource)
                .map(resourceStructInterface -> (ResourceStruct) resourceStructInterfaces)
                .forEach(player::addResource);

        resourceStructInterfaces.stream()
                .filter(ResourceStructInterface::isAnimal)
                .map(resourceStructInterface -> (AnimalStruct) resourceStructInterfaces)
                .forEach(player::addAnimal);
    }
}
