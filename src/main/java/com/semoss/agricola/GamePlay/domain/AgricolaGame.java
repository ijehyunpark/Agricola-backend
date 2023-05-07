package com.semoss.agricola.GamePlay.domain;

import com.semoss.agricola.GamePlay.domain.gameBoard.GameBoard;
import com.semoss.agricola.GamePlay.domain.player.Player;
import com.semoss.agricola.GameRoom.domain.Game;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

/**
 * 아그리콜라 게임
 */

@Getter
public class AgricolaGame implements Game {
    private final GameBoard gameBoard;
    private final List<Player> players;
    private int round;

    @Builder
    public AgricolaGame(GameBoard gameBoard, List<Player> players) {
        this.gameBoard = gameBoard;
        this.players = players;
        this.round = -1;
    }

    /**
     * 새로운 라운드를 시작한다
     */
    public void roundStart() {
        // 라운드를 증가시킨다.
        this.round++;
    }

    /**
     * 모든 플레이어가 해당 라운드에 플레이를 모두 완료했는지 검증
     * @return
     */
    public boolean isAllPlayerPlayed(){
        for(Player player : players){
            if(player.isCompletedPlayed() == false)
                return false;
        }
        return true;
    }

    /**
     * 모든 플레리어의 행동 여부를 초기화한다.
     */
    public void initPlayerPlayed() {
        for(Player player : players){
            player.initPlayed();
        }
    }

    /**
     * 아이 성장
     */
    public void growUpChild() {
        for(Player player : players){
            player.growUpChild();
        }
    }

    //로그 기능

}
