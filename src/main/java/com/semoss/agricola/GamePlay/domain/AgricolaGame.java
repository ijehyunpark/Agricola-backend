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
        @JsonProperty("playerId")
        @JsonIdentityReference(alwaysAsId = true)
        @JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@userId")
        private Player player;
        private void update(GameProgress gameProgress, Player player) {
            this.gameProgress = gameProgress;
            this.player = player;
        }
    }

    private final GameBoard gameBoard;
    private final List<Player> players;

    @JsonProperty("startingPlayerId")
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
     * @param player 게임 진행사항에서 요청을 보내 플레이할 플레이어
     */
    public void update(GameProgress gameProgress, Player player) {
        this.gameState.update(gameProgress, player);
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

    /**
     * 기준 플레이어의 다음 플레이할 플레이어를 찾는다.
     * @param player 기준 플레이어
     * @return 다음 플레이어
     */
    public Player findNextPlayer(Player player) {
        // 현재 차례의 플레이어 인덱스 검색
        int index = players.indexOf(player);
        if (index == -1 || players.size() == 0)
            throw new RuntimeException("다음 유저를 찾을 수 없습니다.");

        // 다음플레이어 반환
        if (index == players.size())
            return players.get(0);

        return players.get(index + 1);
    }

    /**
     * 플레이를 하지 않은 다음 플레이어를 찾는다.
     * @param player 현재 플레이를 진행한 플레이어
     * @return 모든 플레이어가 플레이를 마친 경우 null, 아닌 경우 다음 플레이어
     */
    public Optional<Player> findNextActionPlayer(Player player){
        Player nextPlayer = findNextPlayer(player);
        while(nextPlayer != player){
            if(!nextPlayer.isCompletedPlayed())
                return Optional.of(nextPlayer);
        }
        return Optional.empty();
    }


    /**
     * 새로운 라운드를 시작한다
     */
    public void increaseRound() {
        // 라운드를 증가시킨다.
        this.round++;
    }

    /**
     * 모든 플레리어의 행동 여부를 초기화한다.
     */
    public void initPlayerPlayed() {
        // 플레이어 플레이 초기화
        for(Player player : players){
            player.initPlayed();
        }

        // 액션 초기화
        this.gameBoard.initPlayed();
    }

    /**
     * 아이 성장
     */
    public void growUpChild() {
        for(Player player : players){
            player.growUpChild();
        }
    }

    /**
     * 현재 게임보드의 모든 누적 액션의 누적자원량을 증가시킨다.
     */
    public void processStackEvent() {
        this.gameBoard.processStackEvent(this.round);
    }

    /**
     * 현재 게임보드에 예약된 자원량을 플레이어에게 선물한다.
     */
    public void processReservationResource() {
        this.gameBoard.processReservationResource(this.round);
    }

    /**
     * 액션을 플레이한다.
     * @param eventId 플레이할 액션
     * @param acts 액션에 필요한 추가 요청
     */
    public void playAction(Long eventId, Object acts) {
        // 거주자 한명을 임의로 뽑아 플레이 시킨다.
        this.getGameState().getPlayer().playAction();

        // 액션 플레이를 수행한다.
        this.gameBoard.playAction(this.getGameState().getPlayer(), eventId, acts);
    }

    //로그 기능

}
