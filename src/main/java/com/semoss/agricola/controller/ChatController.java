package com.semoss.agricola.controller;

import com.semoss.agricola.domain.ChatMessage;
import com.semoss.agricola.domain.GameRoom;
import com.semoss.agricola.domain.MessageType;
import com.semoss.agricola.domain.User;
import com.semoss.agricola.service.GameRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;

import java.util.NoSuchElementException;

@Controller
@RequiredArgsConstructor
public class ChatController {

    private final SimpMessageSendingOperations simpMessageSendingOperations;
    private final GameRoomService gameRoomService;

    @MessageMapping("/greetings/{gameRoomId}")
    public void greeting(@DestinationVariable Long gameRoomId, @Payload String username, SimpMessageHeaderAccessor headerAccessor) {
        // 해당 id의 게임룸이 존재하지 않는다. return WS STATUS ?
        GameRoom gameRoom = gameRoomService.getGameRoom(gameRoomId).orElseThrow(NoSuchElementException::new);

        // 새로운 게스트 계정 생성
        User user = User.builder()
                .username(username)
                .build();

        // 웹소켓 연결상 세션에 유저 고유 식별자 저장
        headerAccessor.getSessionAttributes().put("userId", user.getId());

        // 게임룸에도 저장
        gameRoom.getParticipants().add(user);

        // 참여 메세지 제작
        ChatMessage message = ChatMessage.builder()
                .sender(user)
                .type(MessageType.GREETING)
                .build();

        // 그리고 전송
        simpMessageSendingOperations.convertAndSend("/sub/channel/" + gameRoomId, message);
    }

    @MessageMapping("/send/{gameRoomId}")
    public void sendMessage(@DestinationVariable Long gameRoomId, @Payload String content, SimpMessageHeaderAccessor headerAccessor) {
        // 해당 id의 게임룸이 존재하지 않는다. return WS STATUS ?
        if(!gameRoomService.existGameRoom(gameRoomId))
            throw new NoSuchElementException();

        // 누가 보낸 메세지 일까?
        Long userId = (Long) headerAccessor.getSessionAttributes().get("userId");
        User user = gameRoomService.getUser(gameRoomId, userId).orElseThrow(NoSuchElementException::new);

        // 참여 메세지 제작
        ChatMessage message = ChatMessage.builder()
                .sender(user)
                .type(MessageType.MESSAGE)
                .content(content)
                .build();

        // 그리고 전송
        simpMessageSendingOperations.convertAndSend("/sub/channel/" + gameRoomId, message);
    }

    @MessageMapping("/exit/{gameRoomId}")
    public void exitGameRoom(@DestinationVariable Long gameRoomId, SimpMessageHeaderAccessor headerAccessor){
        // 해당 id의 게임룸이 존재하지 않는다. return WS STATUS ?
        if(!gameRoomService.existGameRoom(gameRoomId))
            throw new NoSuchElementException();

        // 누가 보낸 메세지 일까?
        Long userId = (Long) headerAccessor.getSessionAttributes().get("userId");
        User user = gameRoomService.getUser(gameRoomId, userId).orElseThrow(NoSuchElementException::new);

        // 참여 메세지 제작
        ChatMessage message = ChatMessage.builder()
                .sender(user)
                .type(MessageType.LEAVE)
                .build();

        // 그리고 전송
        simpMessageSendingOperations.convertAndSend("/sub/channel/" + gameRoomId, message);

        // 생각해보니 유저 삭제하면 기록 사라지네...
        gameRoomService.deleteUser(gameRoomId, userId);
    }
}
