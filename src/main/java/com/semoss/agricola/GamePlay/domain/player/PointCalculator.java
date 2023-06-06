package com.semoss.agricola.GamePlay.domain.player;

import com.semoss.agricola.GamePlay.domain.card.CardDictionary;
import com.semoss.agricola.GamePlay.domain.resource.AnimalType;
import com.semoss.agricola.GamePlay.domain.resource.ResourceType;

import java.util.ArrayList;
import java.util.List;

public class PointCalculator {
    private static final int[] farmPoint = new int[]{-1,-1,1,2,3,4};
    private static final int[] barnPoint = new int[]{-1,1,2,3,4};
    private static final int[] grainPoint = new int[]{-1,1,1,1,2,2,3,3,4};
    private static final int[] vegetablePoint = new int[]{-1,1,2,3,4};
    private static final int[] sheepPoint = new int[]{-1,1,1,1,2,2,3,3,4};
    private static final int[] wildboarPoint = new int[]{-1,1,1,2,2,3,3,4};
    private static final int[] cattlePoint = new int[]{-1,1,2,2,3,3,4};
    private static final int stablePoint = 1;
    private static final int emptyPoint = -1;
    private static final int[] roomPoint = new int[]{0,1,2};
    private static final int familyPoint = 3;
    private static final int begPoint = -3;

    /**
     * 점수 계산기
     * @param player 점수를 계산할 플레이어
     * @param cardDictionary 이번 게임에 사용되는 카드가 저장되어 있는 사전
     * @return 점수
     */
    public static int calculate(Player player, CardDictionary cardDictionary){
        List<Integer> pointList = new ArrayList<>();

        // farm
        pointList.add(farmPoint[Integer.min(player.getNumField(FieldType.FARM),farmPoint.length-1)]);

        // barn
        pointList.add(barnPoint[Integer.min(player.getNumField(FieldType.BARN),barnPoint.length-1)]);

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
        pointList.add(player.getCardBonusPoints(cardDictionary));

        // bonus from card
        pointList.add(player.getCardPointsResource(cardDictionary));

        // begging
        pointList.add(begPoint * player.getResource(ResourceType.BEGGING));

        return pointList.stream()
                .mapToInt(Integer::intValue)
                .sum();
    }
}
