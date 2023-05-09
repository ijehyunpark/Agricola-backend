package com.semoss.agricola.GamePlay.domain.player;

import com.semoss.agricola.GamePlay.domain.resource.Resource;
import com.semoss.agricola.GamePlay.domain.resource.ResourceStruct;
import jdk.jshell.spi.ExecutionControl;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 게임 플레이어
 */
@Getter
@Builder
public class Player {
    private Long userId;
    @Setter
    private boolean startingToken;
    private PlayerResource resources;
    private PlayerBoard playerBoard;
//    private List<Integer> cardHand;
//    private List<Integer> cardField;

    public boolean isCompletedPlayed(){
        return playerBoard.isCompletedPlayed();
    }

    /**
     * 플레이 여부 초기화
     */
    public void initPlayed() {
        this.playerBoard.initPlayed();
    }

    /**
     * 아이 성장
     */
    public void growUpChild() {
        this.playerBoard.growUpChild();
    }

    /**
     * 수확 작업:
     * 플레이어 보드판에 있는 채소와 곡식 종자를 1개 수확한다.
     */
    public void harvest() {
        // 수확 산출물 계산
        List<ResourceStruct> outputs = this.playerBoard.harvest();

        // 플레이어 자원에 추가
        this.resources.addResource(outputs);
    }

    /**
     * 동물 번식시키기:
     * 내 보드판에서 같은 종류의 동물이 2마리 이상 있는 경우 한 마리가 늘어난다(종류별 최대1마리).
     */
    public void breeding() {
        try {
            this.playerBoard.breeding();
        } catch (ExecutionControl.NotImplementedException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 먹여 살리기
     */
    public void feeding() {
        int foodNeeds = this.playerBoard.calculateFoodNeeds();
        if(this.resources.getResources().get(Resource.FOOD) >= foodNeeds)
            this.resources.addResource(Resource.FOOD, -foodNeeds);
        else
            throw new RuntimeException("음식 토큰 부족");
    }
}
