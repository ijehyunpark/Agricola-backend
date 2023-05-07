package com.semoss.agricola.GamePlay.domain;

import com.semoss.agricola.GamePlay.domain.board.PlayerBoard;
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
}
