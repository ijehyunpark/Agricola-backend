package com.semoss.agricola.GameRoomCommunication.exception;

import lombok.extern.log4j.Log4j2;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
@Log4j2
public class CommunicationExceptionHandler {

    @MessageExceptionHandler
    @SendToUser("/errors")
    public String handleStompException(Throwable ex) {
        log.error(ex.getLocalizedMessage());
        return ex.getLocalizedMessage();
    }
}
