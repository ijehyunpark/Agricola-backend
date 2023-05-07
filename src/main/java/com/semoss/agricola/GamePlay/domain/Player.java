package com.semoss.agricola.GamePlay.domain;

import com.semoss.agricola.GamePlay.domain.board.PlayerBoard;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
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
