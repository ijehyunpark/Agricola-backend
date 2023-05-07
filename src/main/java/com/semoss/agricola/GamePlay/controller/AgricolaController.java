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
     * @return 아그리콜라 게임 상태
     */
    @MessageMapping("/start-game/{gameRoomId}")
    public AgricolaGameStatus startGame(@DestinationVariable Long gameRoomId) {
        // 게임 시작 매커니즘 시작
        agricolaService.start(gameRoomId);

        // 게임 룸 반환
        GameRoom gameRoom = gameRoomService.getOne(gameRoomId);

        log.info(AgricolaGameStatus.toDto(gameRoom).toString());
        return AgricolaGameStatus.toDto(gameRoom);
    }

    /**
     * 플레이어 행동 요청
     * @param gameRoomId 게임을 진행할 게임방 식별자
     * @param headerAccessor 웹 소켓 메세지 헤더 접근자
     * @return 아그리콜라 게임 상태
     */
    public AgricolaGameStatus playAction(@DestinationVariable Long gameRoomId, SimpMessageHeaderAccessor headerAccessor) {
        // 현재 플레이어 턴인지 확인
        if(!agricolaService.validatePlayer(gameRoomId, headerAccessor.getSessionAttributes().get("userId")))
            throw new RuntimeException("잘못된 요청");

        // 게임 룸 반환
        GameRoom gameRoom = gameRoomService.getOne(gameRoomId);

        // 플레이어 액션 진행 TODO
        agricolaService.playAction(gameRoomId, null);
        return AgricolaGameStatus.toDto(gameRoom);
    }

    /**
     * 플레이어 교환 요청
     * @param gameRoomId 게임을 진행할 게임방 식별자
     * @param headerAccessor 웹 소켓 메세지 헤더 접근자
     * @return 아그리콜라 게임 상태
     */
    public AgricolaGameStatus playExchange(@DestinationVariable Long gameRoomId, SimpMessageHeaderAccessor headerAccessor) {
        // 현재 플레이어 턴인지 확인
        if(!agricolaService.validatePlayer(gameRoomId, headerAccessor.getSessionAttributes().get("userId")))
            throw new RuntimeException("잘못된 요청");

        // 게임 룸 반환
        GameRoom gameRoom = gameRoomService.getOne(gameRoomId);

        // 플레이어 교환 진행 TODO
        agricolaService.playExchange(gameRoomId, null);
        return AgricolaGameStatus.toDto(gameRoom);
    }

}
