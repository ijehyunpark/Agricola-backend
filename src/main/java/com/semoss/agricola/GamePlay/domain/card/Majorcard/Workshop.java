package com.semoss.agricola.GamePlay.domain.card.Majorcard;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.semoss.agricola.GamePlay.domain.card.CookingHarvestTrigger;
import com.semoss.agricola.GamePlay.domain.player.Player;
import com.semoss.agricola.GamePlay.domain.resource.ResourceStruct;
import com.semoss.agricola.GamePlay.domain.resource.ResourceType;
import lombok.Builder;
import lombok.Getter;

/**
 * 제작소
 */
@Getter
public class Workshop extends DefaultMajorCard implements CookingHarvestTrigger, ResourceBonusPointTrigger{
    @JsonIgnore
    private final ResourceStruct resourceToFoodHarvest; // 수확시 1회에 한해 자원을 음식으로 교환
    @JsonIgnore
    private final ResourceType resourceTypeToPoints; // 점수계산시 사용하는 자원
    @JsonIgnore
    private final int[][] resourceNumToPoints; // [[개수,점수],[]] 가장 뒤에서부터 만족하는 것 적용(가장 큰점수 적용)

    @Builder
    public Workshop(Long cardID, int bonusPoint, ResourceStruct[] ingredients, String name, String description, ResourceStruct resourceToFoodHarvest, ResourceType resourceTypeToPoints, int[][] resourceNumToPoints) {
        super(cardID, bonusPoint, ingredients, name, description);
        this.resourceToFoodHarvest = resourceToFoodHarvest;
        this.resourceTypeToPoints = resourceTypeToPoints;
        this.resourceNumToPoints = resourceNumToPoints;
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

}
