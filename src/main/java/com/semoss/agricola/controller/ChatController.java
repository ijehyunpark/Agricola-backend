package com.semoss.agricola.controller;

import com.semoss.agricola.domain.ChatMessage;
import com.semoss.agricola.domain.GameRoom;
import com.semoss.agricola.domain.MessageType;
import com.semoss.agricola.domain.User;
import com.semoss.agricola.service.GameRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.Header;
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

    /**
     * 처음 채팅 방에 입장할 때 유저이름을 등록합니다.
     * @param gameRoomId 입장할 채팅 방 고유 식별자
     * @param username 입장할 유저가 사용할 이름
     */
    @MessageMapping("/greetings/{gameRoomId}")
    public void greeting(@DestinationVariable Long gameRoomId, @Payload String username, SimpMessageHeaderAccessor headerAccessor, @Header("simpSessionId") String sessionId) {
        // 해당 id의 게임룸이 존재하지 않는다. return WS STATUS ?
        GameRoom gameRoom = gameRoomService.getGameRoom(gameRoomId).orElseThrow(NoSuchElementException::new);

        if(gameRoom.getParticipants().size() >= gameRoom.getCapacity()){
            ChatMessage message = ChatMessage.builder()
                    .type(MessageType.DENIED)
                    .build();
            simpMessageSendingOperations.convertAndSend("/sub/channel/" + gameRoomId, message);
            throw new RuntimeException("참여 인원이 가득찼습니다.");
        }

        // 새로운 게스트 계정 생성
        User user = User.builder()
                .username(username)
                .gameRoom(gameRoom)
                .build();

        // 게임룸에도 저장
        gameRoom.getParticipants().add(user);

        // 웹소켓 연결상 세션에 유저 저장
        headerAccessor.getSessionAttributes().put("user", user);

        // 참여 메세지 제작
        ChatMessage message = ChatMessage.builder()
                .sender(user)
                .type(MessageType.GREETING)
                .build();

        // 그리고 전송
        simpMessageSendingOperations.convertAndSend("/sub/channel/" + gameRoomId, message);
    }

    /**
     * 채팅방에 메세지를 방송합니다.
     * @param content 채팅 메세지
     */
    @MessageMapping("/send")
    public void sendMessage(@Payload String content, SimpMessageHeaderAccessor headerAccessor) {
        // 누가 보낸 메세지 일까?
        User user = (User) headerAccessor.getSessionAttributes().get("user");

        // 참여 메세지 제작
        ChatMessage message = ChatMessage.builder()
                .sender(user)
                .type(MessageType.MESSAGE)
                .content(content)
                .build();

        // 그리고 전송
        simpMessageSendingOperations.convertAndSend("/sub/channel/" + user.getGameRoom().getId(), message);
    }

}
