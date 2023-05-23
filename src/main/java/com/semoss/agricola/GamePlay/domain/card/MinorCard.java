package com.semoss.agricola.GamePlay.domain.card;

import com.semoss.agricola.GamePlay.domain.player.AnimalStruct;
import com.semoss.agricola.GamePlay.domain.player.Player;
import com.semoss.agricola.GamePlay.domain.resource.ResourceStruct;
import com.semoss.agricola.GamePlay.domain.resource.ResourceType;
import lombok.Getter;
import lombok.Setter;

@Getter
public class MinorCard implements Card{

    private CardType cardType;
    private Long cardID;
    private int bonusPoint;
    private ResourceStruct[] ingredients;
    //private Long[] ingredientCards; // 카드로 가져가는 기능 보류

    private ResourceStruct resourceToFoodHarvest; // 수확시 1회에 한해 자원을 음식으로 교환
    private ResourceStruct[] resourcesToFoodAnytime; // 언제든지 음식으로 교환
    private AnimalStruct[] animalsToFoodAnytime; // 언제든지 음식으로 교환

    private ResourceType resourceTypeToPoints; // 점수계산시 사용하는 자원
    private int[][] resourceNumToPoints; // [[개수,점수],[]] 가장 뒤에서부터 만족하는 것 적용(가장 큰점수 적용)

    private int bakeEfficiency; // 빵굽기 효율 == 곡물 하나당 음식 개수

    @Getter
    @Setter
    private Long owner;

    public MinorCard(Long id){
        cardType = CardType.MINOR;
        cardID = id;
        owner = null;
    }

    /**
     * 플레이어가 카드를 가져가기에 충분한 자원을 가지고 있는지 확인
     * @param player 확인할 플레이어
     * @return 확인 결과
     */
    @Override
    public boolean checkPrerequisites(Player player){
        if (owner != null) return false;
        boolean result = true;
        for (ResourceStruct ingredient : ingredients){
            result &= player.getResource(ingredient.getResource()) >= ingredient.getCount();
        }
        return result;
    }

    /**
     * 사용자가 가지고 있는 보너스 자원의 개수로 점수 계산
     * @param player 확인할 플레이어 -- 가능하면 owner를 이용하도록 변경하고 싶습니다
     * @return 점수
     */
    public int checkPoint(Player player){
        if (resourceNumToPoints == null) return 0;
        int num = player.getResource(resourceTypeToPoints);
        int result = 0;
        for (int[] point : resourceNumToPoints){
            if(point[0] > num) return result;
            result = point[1];
        }
        return result;
    }

    /**
     * 카드를 가져가는데 필요한 자원만큼 player 의 자원을 가져감
     * @param player 카드를 가져갈 플레이어
     */
    @Override
    public void useResource(Player player) {
        setOwner(player.getUserId());
        for (ResourceStruct ingredient : ingredients){
            player.useResource(ingredient.getResource(), ingredient.getCount());
        }
        player.getMajorCard(cardID);
    }
}
