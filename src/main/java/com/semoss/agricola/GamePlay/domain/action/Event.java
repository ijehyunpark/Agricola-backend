package com.semoss.agricola.GamePlay.domain.action;

import com.semoss.agricola.GamePlay.domain.resource.ResourceStruct;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class Event {
    private List<Action> actions;
    private List<DoType> actionDoType;
    // 누적 쌓인 자원
    private List<ResourceStruct> stacks;

    /**
     * 유저 예약 자원
     */
    private class Reservation{
        ResourceStruct resources;
        private Long userId;
    }
    private List<Reservation> reservations;

    private int round;
    private Long playerUsed;

//    private boolean usable;
//    private int doType;
//    private boolean face;

    public void runActions() {
        // TODO: 유저 입력을 받아 actions + actionDoTYPE으로 순회하며 구현
    }
}
