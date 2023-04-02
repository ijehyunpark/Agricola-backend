package com.semoss.agricola.controller;

import com.semoss.agricola.dto.GameRoomCreateRequest;
import com.semoss.agricola.dto.GameRoomResponse;
import com.semoss.agricola.service.GameRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "rooms")
@RequiredArgsConstructor
public class GameRoomController {

    private final GameRoomService gameRoomService;

    /**
     * 존재하는 모든 게임방을 반환합니다.
     * @return 채팅방 리스트
     */
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<GameRoomResponse> searchAllGameRoom(){
        return gameRoomService.getGameRoomList();
    }

    /**
     * 새로운 게임방을 생성합니다.
     * @param gameRoomCreateRequest 게임방 생성에 필요한 정보
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void makeGameRoom(@RequestBody GameRoomCreateRequest gameRoomCreateRequest) {
        gameRoomService.createGameRoom(gameRoomCreateRequest);
    }

    /**
     * 기존 게임방을 삭제합니다.
     * @param id 삭제할 게임방의 고유 식별자
     */
    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteGameRoom(@PathVariable Long id) {
        gameRoomService.destroyGameRoom(id);
    }
}
