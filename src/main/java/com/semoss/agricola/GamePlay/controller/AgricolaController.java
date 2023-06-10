package com.semoss.agricola.GamePlay.controller;

import com.semoss.agricola.GamePlay.dto.AgricolaActionRequest;
import com.semoss.agricola.GamePlay.dto.AgricolaExchangeRequest;
import com.semoss.agricola.GamePlay.dto.AgricolaRelocationRequest;
import com.semoss.agricola.GamePlay.exception.AnimalOverflowException;
import com.semoss.agricola.GamePlay.exception.BlockingException;
import com.semoss.agricola.GamePlay.service.AgricolaService;
import com.semoss.agricola.GameRoom.domain.GameRoom;
import com.semoss.agricola.GameRoom.service.GameRoomService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Log4j2
public class AgricolaController {

    private final SimpMessageSendingOperations simpMessageSendingOperations;
    private final AgricolaService agricolaService;
    private final GameRoomService gameRoomService;

    /**
     * 게임 시작 요청
     * @param gameRoomId 게임을 진행할 게임방 식별자
     */
    @MessageMapping("/start-game/{gameRoomId}")
    public void startGame(@DestinationVariable Long gameRoomId) {
        // 게임 시작 매커니즘 시작
        agricolaService.start(gameRoomId);

        // 게임 룸 반환
        GameRoom gameRoom = gameRoomService.getOne(gameRoomId);

        // 게임 상태 전송
        simpMessageSendingOperations.convertAndSend("/sub/channel/" + gameRoomId, gameRoom);

    }

    /**
     * 플레이어 행동 요청
     * @param gameRoomId 게임을 진행할 게임방 식별자
     * @param actionRequest 행동 요청 필드
     * @param headerAccessor 웹 소켓 메세지 헤더 접근자
     */
    @MessageMapping("/play-action/{gameRoomId}")
    public void playAction(@DestinationVariable Long gameRoomId, @Payload @Valid AgricolaActionRequest actionRequest, SimpMessageHeaderAccessor headerAccessor) {
        // 현재 플레이어 턴인지 확인
        if(!agricolaService.validatePlayer(gameRoomId, headerAccessor.getSessionAttributes().get("userId")))
            throw new RuntimeException("잘못된 요청");

        // 게임 룸 반환
        GameRoom gameRoom = gameRoomService.getOne(gameRoomId);

        // 플레이어 액션 진행
        try {
            agricolaService.playAction(gameRoomId, actionRequest.getEventId(), actionRequest.getActs());
        } catch (BlockingException ex) {
            log.debug("게임이 일시중단되었습니다.");
            simpMessageSendingOperations.convertAndSend("/sub/channel/" + gameRoomId, gameRoom);
            simpMessageSendingOperations.convertAndSend("/sub/errors" , ex.getLocalizedMessage());
        } catch (AnimalOverflowException ex) {
            log.debug("게임이 일시중단되었습니다. : 동물 재배치 필요");
            simpMessageSendingOperations.convertAndSend("/sub/channel/" + gameRoomId, gameRoom);
            simpMessageSendingOperations.convertAndSend("/sub/errors" , ex.getLocalizedMessage());
        }

        // 게임 상태 전송
        simpMessageSendingOperations.convertAndSend("/sub/channel/" + gameRoomId, gameRoom);
    }

    /**
     * 플레이어 교환 요청
     * @param gameRoomId 게임을 진행할 게임방 식별자
     * @param exchangeRequest 교환 요청 필드
     * @param headerAccessor 웹 소켓 메세지 헤더 접근자
     */
    @MessageMapping("/play-exchange/{gameRoomId}")
    public void playExchange(@DestinationVariable Long gameRoomId, @Payload @Valid List<AgricolaExchangeRequest> exchangeRequest, SimpMessageHeaderAccessor headerAccessor) {
        // 현재 플레이어 턴인지 확인
        if(!agricolaService.validatePlayer(gameRoomId, headerAccessor.getSessionAttributes().get("userId")))
            throw new RuntimeException("잘못된 요청");

        // 게임 룸 반환
        GameRoom gameRoom = gameRoomService.getOne(gameRoomId);

        // 플레이어 교환 진행
        try{
            for(AgricolaExchangeRequest exchange : exchangeRequest){
                if(exchange.getResource() != null)
                    agricolaService.playExchange(gameRoomId, exchange.getImprovementId(), exchange.getResource(), exchange.getCount());
                else
                    agricolaService.playExchange(gameRoomId, exchange.getImprovementId(), exchange.getAnimal(), exchange.getCount());
            }
        } catch (BlockingException ex) {
            log.debug("게임이 일시중단되었습니다.");
            simpMessageSendingOperations.convertAndSend("/sub/channel/" + gameRoomId, gameRoom);
            simpMessageSendingOperations.convertAndSend("/sub/errors" , ex.getLocalizedMessage());
        } catch (AnimalOverflowException ex) {
            log.debug("게임이 일시중단되었습니다. : 동물 재배치 필요");
            simpMessageSendingOperations.convertAndSend("/sub/channel/" + gameRoomId, gameRoom);
            simpMessageSendingOperations.convertAndSend("/sub/errors" , ex.getLocalizedMessage());
        }

        // 게임 상태 전송
        simpMessageSendingOperations.convertAndSend("/sub/channel/" + gameRoomId, gameRoom);
    }

    /**
     * 가축 재배치 요청
     * @param gameRoomId 게임을 진행할 게임방 식별자
     * @param relocationRequest 교환 요청 필드
     * @param headerAccessor 웹 소켓 메세지 헤더 접근자
     */
    @MessageMapping("/play-relocation/{gameRoomId}")
    public void playRelocation(@DestinationVariable Long gameRoomId, @Payload @Valid List<AgricolaRelocationRequest> relocationRequest, SimpMessageHeaderAccessor headerAccessor) {
        // 현재 플레이어 턴인지 확인
        if(!agricolaService.validatePlayer(gameRoomId, headerAccessor.getSessionAttributes().get("userId")))
            throw new RuntimeException("잘못된 요청");


        // 게임 룸 반환
        GameRoom gameRoom = gameRoomService.getOne(gameRoomId);

        // 가축 재배치 진행
        try{
            for(AgricolaRelocationRequest relocate : relocationRequest) {
                if (relocate.getAnimal() != null)
                    agricolaService.playRelocation(gameRoomId, relocate.getAnimal(), relocate.getNewY(), relocate.getNewX(), relocate.getCount());
                else
                    agricolaService.playRelocation(gameRoomId, relocate.getY(), relocate.getX(), relocate.getNewY(), relocate.getNewX(), relocate.getCount());
            }
        } catch (BlockingException ex) {
            log.debug("게임이 일시중단되었습니다.");
            simpMessageSendingOperations.convertAndSend("/sub/channel/" + gameRoomId, gameRoom);
            simpMessageSendingOperations.convertAndSend("/sub/errors" , ex.getLocalizedMessage());
        } catch (AnimalOverflowException ex) {
            log.debug("게임이 일시중단되었습니다. : 동물 재배치 필요");
            simpMessageSendingOperations.convertAndSend("/sub/channel/" + gameRoomId, gameRoom);
            simpMessageSendingOperations.convertAndSend("/sub/errors" , ex.getLocalizedMessage());
        }

        // 게임 상태 전송
        simpMessageSendingOperations.convertAndSend("/sub/channel/" + gameRoomId, gameRoom);
    }

}
