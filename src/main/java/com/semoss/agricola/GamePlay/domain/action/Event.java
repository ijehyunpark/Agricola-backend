package com.semoss.agricola.GamePlay.domain.action;

import com.semoss.agricola.GamePlay.domain.resource.Reservation;
import com.semoss.agricola.GamePlay.domain.resource.ResourceStruct;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class Event {
    private final List<Action> actions;
    private final List<DoType> actionDoType;
    private final List<ResourceStruct> stacks; // 누적 쌓인 자원
    private final List<Reservation> reservations; // 예약으로 쌓인 자원

    private int roundGroup;
    @Setter
    private int round;
    private Long playerUsed;

//    private boolean usable;
//    private int doType;
//    private boolean face;

    @Builder
    public Event(List<Action> actions, List<DoType> actionDoType, int roundGroup) {
        this.actions = actions;
        this.actionDoType = actionDoType;
        this.stacks = new ArrayList<>();
        this.reservations = new ArrayList<>();
        this.roundGroup = roundGroup;
        this.playerUsed = null;
    }

    public void runActions() {
        // TODO: 유저 입력을 받아 actions + actionDoTYPE으로 순회하며 구현
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
}
