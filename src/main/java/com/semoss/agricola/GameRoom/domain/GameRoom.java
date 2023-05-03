package com.semoss.agricola.GameRoom.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * 게임의 전제조건과 진행사항을 관리할 컨테이너
 */
@Getter
public class GameRoom {
    private static long NextGameRoomID = 1L;
    private final Long id;
    @Setter
    private String name;
    private int capacity;
    private final List<User> participants;

    @Builder
    public GameRoom(String name, int capacity) {
        this.id = NextGameRoomID++;
        this.name = name;
        this.capacity = capacity;
        this.participants = new ArrayList<>();
    }

    public void setCapacity(int capacity) {
        if (this.participants.size() > capacity)
            throw new IllegalStateException("현재 참여자 수가 설정한 최대 참여자 수보다 많습니다.");
        this.capacity = capacity;
    }
}
