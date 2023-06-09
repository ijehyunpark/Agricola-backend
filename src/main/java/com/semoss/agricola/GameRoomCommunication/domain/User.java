package com.semoss.agricola.GameRoomCommunication.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * 게임방에 찹가한 플레이어 객체
 */
@Getter
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
