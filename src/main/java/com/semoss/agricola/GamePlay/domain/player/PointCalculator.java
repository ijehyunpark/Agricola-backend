package com.semoss.agricola.GamePlay.domain.player;

import com.semoss.agricola.GamePlay.domain.resource.ResourceType;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
public class PointCalculator {
    private int[] farmPoint = new int[]{-1,-1,1,2,3,4};
    private int[] barnPoint = new int[]{-1,1,2,3,4};
    private int[] grainPoint = new int[]{-1,1,1,1,2,2,3,3,4};
    private int[] vegetablePoint = new int[]{-1,1,2,3,4};
    private int[] sheepPoint = new int[]{-1,1,1,1,2,2,3,3,4};
    private int[] wildboarPoint = new int[]{-1,1,1,2,2,3,3,4};
    private int[] cattlePoint = new int[]{-1,1,2,2,3,3,4};
    private int stablePoint = 1;
    private int emptyPoint = -1;
    private int[] roomPoint = new int[]{0,1,2};
    private int familyPoint = 3;
    private int begPoint = -3;

    public int calculate(Player player){
        List<Integer> pointList = new ArrayList<>();

        // farm
        pointList.add(farmPoint[Integer.min(player.numField(FieldType.FARM),farmPoint.length-1)]);

        // barn
        pointList.add(barnPoint[Integer.min(player.numField(FieldType.BARN),barnPoint.length-1)]);

        // grain
        pointList.add(grainPoint[Integer.min(player.getResource(ResourceType.GRAIN),grainPoint.length-1)]);

        // vegetable
        pointList.add(vegetablePoint[Integer.min(player.getResource(ResourceType.VEGETABLE),vegetablePoint.length-1)]);

        // animal
        pointList.add(sheepPoint[Integer.min(player.getAnimal(AnimalType.SHEEP),sheepPoint.length-1)]);
        pointList.add(wildboarPoint[Integer.min(player.getAnimal(AnimalType.WILD_BOAR),wildboarPoint.length-1)]);
        pointList.add(cattlePoint[Integer.min(player.getAnimal(AnimalType.CATTLE),cattlePoint.length-1)]);

        // fenced stable
        pointList.add(stablePoint * player.numStableInBarn());

        // empty field
        pointList.add(emptyPoint * player.numEmptyField());

        // room
        pointList.add(roomPoint[player.getRoomType().getValue()] * player.getRoomCount());

        // family
        pointList.add(familyPoint * player.getFamilyNumber());

        // bonus point
        pointList.add(player.getCardBonusPoints());

        // bonus from card
        pointList.add(player.getCardPointsResource());

        // begging
        pointList.add(begPoint * player.getResource(ResourceType.BEGGING));

        return pointList.stream()
                .mapToInt(Integer::intValue)
                .sum();
    }
}
