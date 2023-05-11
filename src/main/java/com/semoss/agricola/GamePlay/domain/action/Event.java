package com.semoss.agricola.GamePlay.domain.action;

import com.semoss.agricola.GamePlay.domain.resource.Reservation;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Event {
    private final List<Action> actions;
    private final List<DoType> actionDoType;
//    private final List<ResourceStruct> stacks; // 누적 쌓인 자원
    private final List<Reservation> reservations; // 예약으로 쌓인 자원

    private int round;
    private Long playerUsed;

//    private boolean usable;
//    private int doType;
//    private boolean face;

    @Builder
    public Event(List<Action> actions, List<DoType> actionDoType, int round) {
        this.actions = actions;
        this.actionDoType = actionDoType;
//        this.stacks = new ArrayList<>();
        this.reservations = new ArrayList<>();
        this.round = round;
        this.playerUsed = null;
    }

    public void runActions() {
        // TODO: 유저 입력을 받아 actions + actionDoTYPE으로 순회하며 구현
    }
}
