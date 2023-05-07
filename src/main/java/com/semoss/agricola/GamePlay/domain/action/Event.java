package com.semoss.agricola.GamePlay.domain.action;

import com.semoss.agricola.GamePlay.domain.resource.Reservation;
import com.semoss.agricola.GamePlay.domain.resource.ResourceStruct;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class Event {
    private List<Action> actions;
    private List<DoType> actionDoType;
    private List<ResourceStruct> stacks; // 누적 쌓인 자원
    private List<Reservation> reservations; // 예약으로 쌓인 자원

    private int round;
    private Long playerUsed;

//    private boolean usable;
//    private int doType;
//    private boolean face;

    public void runActions() {
        // TODO: 유저 입력을 받아 actions + actionDoTYPE으로 순회하며 구현
    }
}
