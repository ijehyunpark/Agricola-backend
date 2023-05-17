package com.semoss.agricola.GamePlay.domain.card;

import com.semoss.agricola.GamePlay.domain.player.Player;
import com.semoss.agricola.GamePlay.domain.resource.ResourceType;
import lombok.Getter;
import lombok.Setter;

public class MajorCard implements Card{
    @Getter
    private CardType cardType = CardType.MAJOR;
    private String cardID;

    private int bonusPoint;

    private ResourceType resourceTypeToFoodHarvest; // 수확시 1회에 한해 자원을 음식으로 교환
    private int resourceNumToFoodHarvest; // 수확시 1회에 한해 자원을 음식으로 교환
    private ResourceType[] resourceTypesToFoodAnytime; // 언제든지 음식으로 교환
    private int[] resourceNumsToFoodAnytime; // 언제든지 음식으로 교환
    private ResourceType resourceTypeToPoints; // 점수계산시 사용하는 자원
    private int[][] resourceNumToPoints; // [[개수,점수],[]] 가장 뒤에서부터 만족하는 것 적용(가장 큰점수 적용)
    private boolean immediateBakeAction; // 빵굽기 즉시행동 여부
    private int foodEfficiency; // 빵굽기 효율 == 곡물 하나당 음식 개수
    private int bakeActionNum; // 빵굽기 횟수
    /**
     * 빵굽기시에 빵굽기 횟수 >= 현재 빵굽기 횟수인 경우에만 빵굽기 효율이 적용됨됨
     */

    @Getter
    @Setter
    private Long owner;


    public MajorCard(String cardID){
        this.cardID = cardID;

    }

    @Override
    public boolean checkPrerequisites(Player player) {
        return false;
    }

    @Override
    public CardType getCardType() {
        return null;
    }

    @Override
    public Long getOwner() {
        return null;
    }

    @Override
    public void setOwner(Long userID) {

    }

    @Override
    public void useResource(Player player) {

    }
}
