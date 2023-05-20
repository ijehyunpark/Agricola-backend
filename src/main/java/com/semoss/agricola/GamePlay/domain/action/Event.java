package com.semoss.agricola.GamePlay.domain.action;

import com.fasterxml.jackson.annotation.*;
import com.semoss.agricola.GamePlay.domain.card.Card;
import com.semoss.agricola.GamePlay.domain.gameboard.GameBoard;
import com.semoss.agricola.GamePlay.domain.player.Player;
import com.semoss.agricola.GamePlay.domain.resource.Reservation;
import com.semoss.agricola.GamePlay.domain.resource.ResourceStruct;
import com.semoss.agricola.GamePlay.dto.AgricolaActionRequest;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class Event {
    private GameBoard gameBoard;
    private final Long id;
    private static Long nextEventID = 1L;
    private final List<Action> actions = new ArrayList<>();
    private final List<DoType> actionDoType = new ArrayList<>();
    private final List<ResourceStruct> stacks = new ArrayList<>(); // 누적 쌓인 자원
    private final List<Reservation> reservations = new ArrayList<>(); // 예약으로 쌓인 자원

    @JsonIgnore
    private int roundGroup;
    @Setter
    private int round;

    @JsonProperty("playerId")
    @JsonIdentityReference(alwaysAsId = true)
    @JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@userId")
    private Player isPlayed;

    @Builder
    public Event(GameBoard gameBoard, List<Action> actions, List<DoType> actionDoType, int roundGroup) {
        this.gameBoard = gameBoard;
        this.id = nextEventID++;
        actions.stream()
                .forEach(action -> this.actions.add(action));
        actionDoType.stream()
                .forEach(doType -> this.actionDoType.add(doType));
        this.roundGroup = roundGroup;
        this.isPlayed = null;
    }

    /**
     * 입력에 대해 액션 수행
     * @param player 액션을 플레이하는 플레이어
     * @param acts 액션 수행관련 세부 사항
     */
    public void runActions(Player player, List<AgricolaActionRequest.ActionFormat> acts) {
        // 이미 플레이된 상태인지 확인
        if(this.isPlayed != null)
            throw new RuntimeException("이미 플레이한 액션칸입니다.");

        this.isPlayed = player;

        // TODO: Object 입력 개선 (object acts.acts)
        this.actions.stream()
                .filter(action -> acts.get(actions.indexOf(action)).getUse())
                .forEach(action -> {
                    AgricolaActionRequest.ActionFormat act = acts.get(actions.indexOf(action));
                    switch (action.getActionType()) {
                        case BASIC, STARTING, UPGRADE, ADOPT -> {
                            ((SimpleAction) action).runAction(player);
                        }
                        case BAKE, BUILD, CULTIVATION -> {
                            ((MultiInputAction) action).runAction(player, act.getActs());
                        }
                        case PLACE -> {
                            Card card = gameBoard.getCardDictionary().getCard((Long) act.getActs());
                            ((PlaceAction) action).runAction(player, card);
                        }
                        case STACK -> {
                            throw new RuntimeException("액션 행동을 지원하지 않는 타입입니다.");
                        }
                    }
                });
    }

    public void stackResource(ResourceStruct resource){
        // 이미 쌓인 자원인 경우 찾아서 더하고, 없는 경우 새로 추가한다.
        stacks.stream()
                .filter(stack -> stack.getResource().equals(resource.getResource()))
                .findFirst()
                .ifPresentOrElse(
                        stack -> stack.addResource(resource.getCount()),
                        () -> stacks.add(resource)
                );
    }

    /**
     * 예약 자원을 플레이어에게 전달하고 삭제한다.
     */
    public void deliverReservation() {
        List<Reservation> toRemove = reservations.stream()
                .peek(Reservation::resolve)
                .collect(Collectors.toList());
        toRemove.forEach(reservations::remove);
    }

    /**
     * 플레이 여부 초기화
     */
    public void initPlayed() {
        this.isPlayed = null;
    }
}
