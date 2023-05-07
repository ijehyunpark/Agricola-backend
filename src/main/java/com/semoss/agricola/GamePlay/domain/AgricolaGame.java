package com.semoss.agricola.GamePlay.domain;

import com.semoss.agricola.GamePlay.domain.board.GameBoard;
import com.semoss.agricola.GameRoom.domain.GameScripts;
import com.semoss.agricola.GameRoomCommunication.domain.User;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

/**
 * 아그리콜라 게임
 */
@Builder
@RequiredArgsConstructor
@Getter
public class AgricolaGame implements GameScripts {
    private final GameBoard gameBoard;
    private final List<Player> player;
    private int round;

    /**
     * 새로운 라운드를 시작한다
     */
    public void roundStart() {
        this.round++;
    }

    @Override
    public List<User> getUser() {
        return null;
    }

    //로그 기능

}
