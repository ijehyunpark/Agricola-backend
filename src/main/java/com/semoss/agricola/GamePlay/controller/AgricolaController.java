package com.semoss.agricola.GamePlay.controller;

import com.semoss.agricola.GamePlay.dto.AgricolaGameStatus;
import com.semoss.agricola.GamePlay.service.AgricolaService;
import com.semoss.agricola.GameRoom.service.GameRoomService;
import com.semoss.agricola.GameRoom.domain.GameRoom;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
@Log4j2
public class AgricolaController {

    private final AgricolaService agricolaService;
    private final GameRoomService gameRoomService;

    /**
     * 게임 시작 요청
     * @param gameRoomId 게임을 진행할 게임방 식별자
     * @param headerAccessor 웹 소켓 메세지 헤더 접근자
     */
    @MessageMapping("/start-game/{gameRoomId}")
    public AgricolaGameStatus startGame(@DestinationVariable Long gameRoomId, SimpMessageHeaderAccessor headerAccessor) {
        // 게임 시작 매커니즘 시작
        agricolaService.start(gameRoomId);

        // 게임 룸 반환
        GameRoom gameRoom = gameRoomService.getOne(gameRoomId);

        log.info(AgricolaGameStatus.toDto(gameRoom).toString());
        return AgricolaGameStatus.toDto(gameRoom);
    }
}
