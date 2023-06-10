package com.semoss.agricola.GamePlay.domain.card;

import com.semoss.agricola.GamePlay.domain.player.Player;
import com.semoss.agricola.GamePlay.domain.resource.AnimalStruct;
import com.semoss.agricola.GamePlay.domain.resource.ResourceStruct;
import com.semoss.agricola.GamePlay.domain.resource.ResourceStructInterface;
import com.semoss.agricola.GamePlay.domain.resource.ResourceType;

import java.util.List;

/**
 * 언제든 음식으로 교환
 */
public interface CookingAnytimeTrigger extends Card {
    List<ResourceStructInterface> getResourcesToFoodAnytime(); // 언제든지 음식으로 교환

    /**
     * 요리한다. (자원을 교환한다.)
     * @param player 요리할 플레이어
     * @param resource 요리할 자원
     */
    default void cooking(Player player, ResourceStructInterface resource) {
        if(resource.isResource()) {
            cookingResource(player, (ResourceStruct) resource);
        } else if(resource.isAnimal()) {
            cookingAnimal(player, (AnimalStruct) resource);
        }
    }
    private void cookingResource(Player player, ResourceStruct resource) {
        // 레시피 탐색
        List<ResourceStructInterface> resourceStructInterfaces = getResourcesToFoodAnytime();
        int recipe = resourceStructInterfaces.stream()
                .filter(ResourceStructInterface::isResource)
                .map(resourceStructInterface -> (ResourceStruct) resourceStructInterfaces)
                .filter(resourceStruct -> resource.getResource() == resourceStruct.getResource())
                .findAny()
                .orElseThrow(IllegalAccessError::new)
                .getCount();

        // 요리 (자원 교환)
        player.useResource(resource);
        player.addResource(ResourceType.FOOD, recipe* resource.getCount());
    }

    private void cookingAnimal(Player player, AnimalStruct animal) {
        // 레시피 탐색
        List<ResourceStructInterface> resourceStructInterfaces = getResourcesToFoodAnytime();
        int recipe = resourceStructInterfaces.stream()
                .filter(ResourceStructInterface::isAnimal)
                .map(resourceStructInterface -> (AnimalStruct) resourceStructInterfaces)
                .filter(animalStruct -> animal.getAnimal() == animalStruct.getAnimal())
                .findAny()
                .orElseThrow(IllegalAccessError::new)
                .getCount();

        // 요리 (자원 교환)
        player.useAnimal(animal.getAnimal(), animal.getCount());
        player.addResource(ResourceType.FOOD, recipe* animal.getCount());
    }
}
