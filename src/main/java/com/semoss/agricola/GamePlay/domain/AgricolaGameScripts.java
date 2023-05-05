package com.semoss.agricola.GamePlay.domain;

import com.semoss.agricola.GameRoom.domain.GameScripts;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Builder
@RequiredArgsConstructor
@Getter
public class AgricolaGameScripts implements GameScripts {
    private final GameBoard gameBoard;
    private final List<Player> player;

    //로그 기능

}
