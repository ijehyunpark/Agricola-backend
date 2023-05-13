package com.semoss.agricola.GamePlay.domain;

import com.fasterxml.jackson.annotation.*;
import com.semoss.agricola.GamePlay.domain.gameboard.GameBoard;
import com.semoss.agricola.GamePlay.domain.player.Player;
import com.semoss.agricola.GameRoom.domain.Game;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

/**
 * 아그리콜라 게임
 */

@Getter
public class AgricolaGame implements Game {

    @Getter
    @Setter
    public class GameState {
        private GameProgress gameProgress;
        private Long userId;
        private void update(GameProgress gameProgress, Long userId) {
            this.gameProgress = gameProgress;
            this.userId = userId;
        }
    }

    private final GameBoard gameBoard;
    private final List<Player> players;

    @JsonProperty("startingPlayerUserId")
    @JsonIdentityReference(alwaysAsId = true)
    @JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@userId")
    private Player startingPlayer;
    private int round;
    private GameState gameState;

    @Builder
    public AgricolaGame(GameBoard gameBoard, List<Player> players) {
        this.gameBoard = gameBoard;
        this.players = players;
        this.startingPlayer = players.size() > 0 ? players.get(0) : null;
        this.round = -1;
        this.gameState = new GameState();
    }

    /**
     * 현재 게임의 진행사항을 업데이트한다.
     * @param gameProgress 현재 진행사항
     * @param userId 다음 진행해야 할 유저 고유 식별자
     */
    public void update(GameProgress gameProgress, Long userId) {
        this.gameState.update(gameProgress, userId);
    }

    /**
     * 선공 플레이어 변경
     */
    public void updateStartingPlayer() {
        // 선공 플레이어 검색
        Optional<Player> startingPlayer = players.stream()
                .filter(player -> player.isStartingToken())
                .findAny();

        // 선공 플레이어 변경
        this.startingPlayer = startingPlayer.orElseThrow(RuntimeException::new);
    }


    public Player findPlayerByUserId(Long userId) {
        // 플레이어 검색
        Optional<Player> targetPlayer = players.stream()
                .filter(player -> player.getUserId() == userId)
                .findAny();

        return targetPlayer.orElseThrow(NoSuchElementException::new);
    }



    public Player findNextPlayer() {
        // 현재 차례의 플레이어 인덱스 검색
        int index = players.indexOf(findPlayerByUserId(gameState.getUserId()));
        if (index == -1 || players.size() == 0)
            throw new RuntimeException("다음 유저를 찾을 수 없습니다.");

        // 다음플레이어 반환
        if (index == players.size())
            return players.get(0);

        return players.get(index + 1);
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
    @JsonIgnore
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
