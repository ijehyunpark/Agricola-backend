package com.semoss.agricola.GamePlay.domain;

import com.semoss.agricola.GamePlay.domain.board.GameBoard;
import com.semoss.agricola.GameRoom.domain.Game;
import com.semoss.agricola.GameRoomCommunication.domain.User;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

/**
 * 아그리콜라 게임
 */

@Getter
public class AgricolaGame implements Game {
    private final GameBoard gameBoard;
    private final List<Player> player;
    private int round;

    @Builder
    public AgricolaGame(GameBoard gameBoard, List<Player> player) {
        this.gameBoard = gameBoard;
        this.player = player;
        this.round = -1;
    }

    /**
     * 새로운 라운드를 시작한다
     */
    public void roundStart() {
        // 라운드를 증가시킨다.
        this.round++;
    }

    @Override
    public List<User> getUser() {
        return null;
    }

    //로그 기능

}
