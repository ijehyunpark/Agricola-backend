package com.semoss.agricola.GamePlay.domain.card;

import com.semoss.agricola.GamePlay.domain.resource.AnimalStruct;
import com.semoss.agricola.GamePlay.domain.player.Player;
import com.semoss.agricola.GamePlay.domain.resource.ResourceStruct;
import com.semoss.agricola.GamePlay.domain.resource.ResourceType;
import lombok.Getter;
import lombok.Setter;

@Getter
public class MinorCard implements Card {

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
        cardType = CardType.OCCUPATION;
        cardID = id;
        owner = null;
    }

    @Override
    public boolean checkPrerequisites(Player player) {
        return true;
    }


    @Override
    public void place(Player player) {

    }
}
