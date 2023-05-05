package com.semoss.agricola.GameRoomCommunication.config;

import com.semoss.agricola.GameRoomCommunication.domain.User;
import com.semoss.agricola.GameRoomCommunication.service.CommunicationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.util.NoSuchElementException;

@Component
@RequiredArgsConstructor
@Log4j2
public class StompWebSocketEventListener {

    private final CommunicationService communicationService;
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
        log.info("세션 연결 종료 : " + sessionId);

        Long userId = (Long) headerAccessor.getSessionAttributes().get("userId");
        Long gameRoomId = (Long) headerAccessor.getSessionAttributes().get("gameRoomId");

        // 그리고 유저 삭제
        try{
            communicationService.exit(gameRoomId, userId);
        } catch (NoSuchElementException ex){
            log.error(ex.getLocalizedMessage());
        }
    }
}
