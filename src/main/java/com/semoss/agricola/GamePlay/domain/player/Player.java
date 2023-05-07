package com.semoss.agricola.GamePlay.domain.player;

import com.semoss.agricola.GamePlay.domain.resource.PlayerResource;
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
    private List<Integer> cardHand;
    private List<Integer> cardField;

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
        playerBoard.growUpChild();
    }
}
