package com.semoss.agricola.config;

import com.semoss.agricola.domain.ChatMessage;
import com.semoss.agricola.domain.MessageType;
import com.semoss.agricola.domain.User;
import com.semoss.agricola.service.GameRoomService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Component
@RequiredArgsConstructor
@Log4j2
public class StompWebSocketEventListener {

    private final GameRoomService gameRoomService;
    private final SimpMessageSendingOperations simpMessageSendingOperations;

    /**
     * 연결 요청 시 발생
     * @param event
     */
    @EventListener
    public void handleWebSocketConnectListener(SessionConnectEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        String sessionId = headerAccessor.getSessionId();
        log.info("Session Connect : " + sessionId);
    }

    /**
     * 연결 성공 시 발생
     * @param event
     */
    @EventListener
    public void handleWebSocketConnectListener(SessionConnectedEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        String sessionId = headerAccessor.getSessionId();
        log.info("Session Connected : " + sessionId);
    }

    /**
     * 연결 종료 시 발생
     * @param event
     */
    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());

        String sessionId = headerAccessor.getSessionId();
        log.info("Session Disconnected : " + sessionId);

        User user = (User) headerAccessor.getSessionAttributes().get("user");

        // 종료 메세지 제작
        ChatMessage message = ChatMessage.builder()
                .sender(user)
                .type(MessageType.LEAVE)
                .build();

        // 그리고 전송
        simpMessageSendingOperations.convertAndSend("/sub/channel/" + user.getGameRoom().getId(), message);

        // 그리고 유저 삭제
        gameRoomService.deleteUser(user.getGameRoom().getId(), user.getId());
    }


}
