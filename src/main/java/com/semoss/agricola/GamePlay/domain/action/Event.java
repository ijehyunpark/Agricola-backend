package com.semoss.agricola.GamePlay.domain.action;

import com.semoss.agricola.GamePlay.domain.resource.Reservation;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Event {
    private final Long id;
    private static Long nextEventID = 1L;
    private final List<Action> actions;
    private final List<DoType> actionDoType;
//    private final List<ResourceStruct> stacks; // 누적 쌓인 자원
    private final List<Reservation> reservations; // 예약으로 쌓인 자원

    private int round;
    private boolean isPlayed;

//    private boolean usable;
//    private int doType;
//    private boolean face;

    @Builder
    public Event(List<Action> actions, List<DoType> actionDoType, int round) {
        this.id = nextEventID++;
        this.actions = actions;
        this.actionDoType = actionDoType;
//        this.stacks = new ArrayList<>();
        this.reservations = new ArrayList<>();
        this.round = round;
        this.isPlayed = false;
    }

    /**
     * 입력에 대해 액션 수행
     * @param acts
     */
    public void runActions(Object acts) {
        if(isPlayed)
            throw new RuntimeException("이미 플레이한 액션칸입니다.");

        // TODO: 유저 입력을 받아 actions + actionDoTYPE으로 순회하며 구현
    }

    /**
     * 플레이 여부 초기화
     */
    public void initPlayed() {
        this.isPlayed = false;
    }
}
