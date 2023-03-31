package com.semoss.agricola.domain;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

/**
 * 채팅 메세지를 표현하는 클래스이다.
 */
@Getter
public class ChatMessage {
    private static long NextID = 0;
    private Long id;
    private String content;
    private User sender;
    private MessageType type;
    private LocalDate createTime;

    @Builder
    public ChatMessage(String content, User sender, MessageType type) {
        this.id = NextID++;
        this.content = content;
        this.sender = sender;
        this.type = type;
        this.createTime = LocalDate.now();
    }
}
