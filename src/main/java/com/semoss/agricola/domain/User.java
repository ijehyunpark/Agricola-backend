package com.semoss.agricola.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;


/**
 * 게임 서비스상 참여자를 나타낸다.
 */

@Getter
public class User {
    private static long NextUserID = 0L;

    private Long id;
    private String username;

    @Builder
    public User(String username) {
        this.id = NextUserID++;
        this.username = username;
    }
}
