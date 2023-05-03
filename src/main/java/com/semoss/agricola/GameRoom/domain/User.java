package com.semoss.agricola.GameRoom.domain;

import lombok.Builder;
import lombok.Setter;

/**
 * 게임방에 찹가한 플레이어 객체
 */
public class User {
    private static long NextUserID = 1L;
    private final Long id;
    @Setter
    private String username;

    @Builder
    public User(String username) {
        this.id = NextUserID++;
        this.username = username;
    }
}
