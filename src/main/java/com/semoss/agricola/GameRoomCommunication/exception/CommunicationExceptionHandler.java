package com.semoss.agricola.GameRoomCommunication.exception;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.web.bind.annotation.ControllerAdvice;

/**
 * 개발 오류에 대해 리포트 하기 위한 오류 메세지 전송
 */
//@ControllerAdvice
@RequiredArgsConstructor
@Log4j2
public class CommunicationExceptionHandler {

    private final SimpMessageSendingOperations simpMessageSendingOperations;
    @MessageExceptionHandler
    public void handleStompException(Throwable ex) {
        log.error(ex.getLocalizedMessage());

        // 에러 상태 전송
        simpMessageSendingOperations.convertAndSend("/sub/errors" , ex.getLocalizedMessage());
    }
}
