package com.semoss.agricola.GamePlay.domain;

import com.semoss.agricola.GamePlay.domain.board.GameBoard;
import com.semoss.agricola.GameRoom.domain.GameScripts;
import com.semoss.agricola.GameRoomCommunication.domain.User;
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

    @Override
    public List<User> getUser() {
        return null;
    }

    //로그 기능

}
