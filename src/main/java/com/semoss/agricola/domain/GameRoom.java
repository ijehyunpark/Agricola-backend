package com.semoss.agricola.domain;


import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * 게임 시작을 위한 준비 방을 표현한 객체이다.
 */
@Getter
public class GameRoom {
    private static long NextGameRoomID = 0L;
    private Long id;
    private String name;
    private int capacity;
    private List<User> participants;

    @Builder
    public GameRoom(String name, int capacity) {
        this.id = NextGameRoomID++;
        this.name = name;
        this.capacity = capacity;
        this.participants = new ArrayList<>();
    }
}
