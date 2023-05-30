package com.semoss.agricola.GamePlay.domain;

import com.fasterxml.jackson.annotation.*;
import com.semoss.agricola.GamePlay.domain.action.Event;
import com.semoss.agricola.GamePlay.domain.action.implement.DefaultAction;
import com.semoss.agricola.GamePlay.domain.card.Card;
import com.semoss.agricola.GamePlay.domain.card.CardDictionary;
import com.semoss.agricola.GamePlay.domain.card.CardType;
import com.semoss.agricola.GamePlay.domain.card.MajorCard;
import com.semoss.agricola.GamePlay.domain.gameboard.GameBoard;
import com.semoss.agricola.GamePlay.domain.gameboard.ImprovementBoard;
import com.semoss.agricola.GamePlay.domain.player.AnimalType;
import com.semoss.agricola.GamePlay.domain.player.Player;
import com.semoss.agricola.GamePlay.domain.resource.ResourceType;
import com.semoss.agricola.GamePlay.dto.AgricolaActionRequest;
import com.semoss.agricola.GamePlay.exception.NotFoundException;
import com.semoss.agricola.GamePlay.exception.NotImplementException;
import com.semoss.agricola.GameRoom.domain.Game;
import com.semoss.agricola.GameRoom.domain.GameType;
import com.semoss.agricola.GameRoomCommunication.domain.User;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.IntStream;

/**
 * 아그리콜라 게임
 */

@Getter
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@JsonPropertyOrder({"gameType", "gameState", "startingPlayerId", "gameBoard", "players", "events", "cardDictionary"})
@Log4j2
public class AgricolaGame implements Game {
    private final GameType gameType = GameType.Agricola;

    @Getter
    public static class GameState {
        private int round = -1;
        private GameProgress gameProgress;
        @JsonProperty("playerId")
        @JsonIdentityReference(alwaysAsId = true)
        @JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@userId")
        private Player player;
        private void update(GameProgress gameProgress, Player player) {
            this.gameProgress = gameProgress;
            this.player = player;
        }
        private void increaseRound() {
            this.round++;
        }
    }

    private final GameBoard gameBoard;
    private final CardDictionary cardDictionary;
    private final List<Player> players;

    @JsonProperty("startingPlayerId")
    @JsonIdentityReference(alwaysAsId = true)
    @JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@userId")
    private Player startingPlayer;
    private final GameState gameState;

    public AgricolaGame(List<User> users, String strategy, List<DefaultAction> actions) {
        log.debug("아그리 콜라 게임생성:" + this.hashCode());
        log.debug("입력된 플레이어:" + users.size());
        log.debug("플레이어 생성 전략:" + strategy);

        if(users.size() == 0)
            throw new RuntimeException("아그리콜라에 필요한 최소 인원수를 충족하지 않았습니다.");
        if(users.size() > 4)
            throw new RuntimeException("아그리콜라에 필요한 최대 인원수를 초과하였습니다.");

        // 카드 사전 제작
        cardDictionary = new CardDictionary();

        // 게임 보드 제작
        this.gameBoard = GameBoard.builder()
                .improvementBoard(new ImprovementBoard(cardDictionary))
                .events(actions.stream()
                        .map(action -> Event.builder().action(action).build())
                        .toList())
                .build();

        // 게임방 유저 객체로 아그리 콜라 게임 플레이어 객체를 생성
        this.players = IntStream.range(0, users.size())
                .mapToObj(i -> {
                    boolean isFirst = i == 0;
                    return Player.builder()
                            .game(this)
                            .userId(users.get(i).getId())
                            .isStartPlayer(isFirst)
                            .build();
                })
                .toList();
        this.startingPlayer = players.get(0);
        gameState = new GameState();
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
                .filter(Player::isStartingToken)
                .findAny();

        // 선공 플레이어 변경
        this.startingPlayer = startingPlayer.orElseThrow(NotFoundException::new);
    }


    public Player findPlayerByUserId(Long userId) {
        // 플레이어 검색
        Optional<Player> targetPlayer = players.stream()
                .filter(player -> player.getUserId().equals(userId))
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
            throw new NotFoundException("다음 유저를 찾을 수 없습니다.");

        // 다음플레이어 반환
        if (index + 1 == players.size())
            return players.get(0);

        return players.get(index + 1);
    }

    /**
     * 플레이를 하지 않은 다음 플레이어를 찾는다.
     * @param player 현재 플레이를 진행한 플레이어
     * @return 모든 플레이어가 플레이를 마친 경우 null, 아닌 경우 다음 플레이어
     */
    public Optional<Player> findNextActionPlayer(Player player){
        Player nextPlayer = player;
        do{
            nextPlayer = findNextPlayer(player);
            if(!nextPlayer.isCompletedPlayed())
                return Optional.of(nextPlayer);
        } while (nextPlayer != player);
        return Optional.empty();
    }


    /**
     * 새로운 라운드를 시작한다
     */
    public void increaseRound() {
        // 라운드를 증가시킨다.
        this.gameState.increaseRound();
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
        this.gameBoard.processStackEvent();
    }

    /**
     * 현재 게임보드에 예약된 자원량을 플레이어에게 선물한다.
     */
    public void processReservationResource() {
        this.gameBoard.processReservationResource(this.gameState.getRound());
    }


    @JsonProperty("events")
    public List<Event> getActiveEvents() {
        return this.getGameBoard().events().stream()
                .filter(event -> event.getRound() <= this.gameState.getRound())
                .toList();
    }

    /**
     * 액션을 플레이한다.
     * @param eventId 플레이할 액션
     * @param acts 액션에 필요한 추가 요청
     */
    public void playAction(Long eventId, List<AgricolaActionRequest.ActionFormat> acts) {
        // 액션 플레이를 수행한다.
        History history = this.gameBoard.playAction(this.getGameState().getPlayer(), eventId, acts, this.cardDictionary);

        // 거주자 한명을 임의로 뽑아 플레이 시킨다.
        this.getGameState().getPlayer().playAction(history);
    }

    /**
     * 교환을 플레이한다.
     * @param improvementId 자원 교환 작업 시 사용할 주설비 식별자
     * @param resource 교환할 자원의 종류와 개수
     */
    public void playExchange(Long improvementId, ResourceType resource, Long count) {
        Player player = this.getGameState().getPlayer();

        // TODO: 플레이어가 해당 주설비를 가지고 있는지 검증한다.
        Card card = cardDictionary.getCard(improvementId);
        if (card.getCardType() != CardType.MAJOR)
            throw new RuntimeException("주설비카드가 아닙니다.");
        if(!player.hasCardInField(((MajorCard) card).getCardID()))
            throw new NotFoundException("주설비를 가지고 있지 않습니다.");

        // TODO : 플레이어가 교환할 자원을 가지고 있는지 검증한다.
        //

        // TODO: 주설비와 교환 요청 자원을 사용하여 교환 작업을 수행한다.
        //
        throw new NotImplementException("미구현");
    }


    public void playExchange(Long improvementId, AnimalType animal, Long count) {
        Player player = this.getGameState().getPlayer();

        // TODO: 플레이어가 해당 주설비를 가지고 있는지 검증한다.
        Card card = cardDictionary.getCard(improvementId);
        if (card.getCardType() != CardType.MAJOR)
            throw new RuntimeException("주설비카드가 아닙니다.");
        if(!player.hasCardInField(((MajorCard) card).getCardID()))
            throw new NotFoundException("주설비를 가지고 있지 않습니다.");

        // TODO : 플레이어가 교환할 자원을 가지고 있는지 검증한다.
        //

        // TODO: 주설비와 교환 요청 자원을 사용하여 교환 작업을 수행한다.
        //
        throw new NotImplementException("미구현");
    }

    public void finish() {
        players.forEach(Player::finish);
    }

    //로그 기능

}
