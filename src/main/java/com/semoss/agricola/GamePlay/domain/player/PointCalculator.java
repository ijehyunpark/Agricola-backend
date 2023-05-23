package com.semoss.agricola.GamePlay.domain.player;

import com.semoss.agricola.GamePlay.domain.resource.ResourceType;

public class PointCalculator {
    public static int calculate(Player player){

        int num;
        int point;
        for (ResourceType resourceType : ResourceType.values()){
            num = player.getResource(resourceType);
        }
        for (AnimalType animalType : AnimalType.values()){

        }
        for (FieldType fieldType : FieldType.values()){
            player.numField(fieldType);
        }
        return 0;
    }
}
