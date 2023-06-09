package com.semoss.agricola.GameRoomCommunication.controller;

import com.semoss.agricola.GameRoomCommunication.domain.User;
import com.semoss.agricola.GameRoomCommunication.dto.GreetingDto;
import com.semoss.agricola.GameRoomCommunication.service.CommunicationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
@Log4j2
public class CommunicationController {

    private final SimpMessageSendingOperations simpMessageSendingOperations;
    private final CommunicationService communicationService;


    /**
     * 게임 방 입장 처리
     * @param gameRoomId 참여할 게임방 식별자
     * @param username 게임방에서 사용할 유저 이름
     * @param headerAccessor 웹 소켓 메세지 헤더 접근자
     */
    @MessageMapping("/greetings/{gameRoomId}")
    @SendToUser("/queue/message")
    public GreetingDto greeting(@DestinationVariable Long gameRoomId, @Payload String username, SimpMessageHeaderAccessor headerAccessor) {
        Object userId = headerAccessor.getSessionAttributes().get("userId");
        if (userId != null)
            throw new RuntimeException("이미 게임방에 참여중인 유저객체가 존재합니다.");

        // 새로운 유저 참여
        User user = communicationService.participate(gameRoomId, username);
        log.info("새로운 회원이 참가하였습니다: " + gameRoomId.toString() + ",유저 이름: " + username);

        // 웹 소켓 세션에 유저 저장
        headerAccessor.getSessionAttributes().put("userId", user.getId());
        headerAccessor.getSessionAttributes().put("gameRoomId", gameRoomId);

        return GreetingDto.builder()
                .username(username)
                .id(user.getId())
                .build();
    }
}
