package com.semoss.agricola.GameRoom.Controller;

import com.semoss.agricola.GameRoom.Service.GameRoomService;
import com.semoss.agricola.GameRoom.dto.GameRoomCreateRequest;
import com.semoss.agricola.GameRoom.dto.GameRoomResponse;
import com.semoss.agricola.GameRoom.dto.GameRoomUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 게임방 생명주기를 관리하는 REST CONTROLLER
 */
@RestController
@RequestMapping(value = "rooms")
@RequiredArgsConstructor
public class GameRoomController {
    private final GameRoomService gameRoomService;

    /**
     * 모든 게임방을 요구하는 요청을 처리한다.
     * @return 현재 존재하는 모든 게임방 리스트
     */
    @GetMapping
    @ResponseStatus(value = HttpStatus.OK)
    public List<GameRoomResponse> searchAllGameRoom(){
        return gameRoomService.getAll().stream()
                .map(GameRoomResponse::toDto)
                .toList();
    }

    /**
     * 특정 id를 가진 게임방을 요구하는 요청을 처리한다.
     * @param id 찾고 싶은 게임방의 고유 식별자
     * @return 해당 게임방의 정보
     */
    @GetMapping(value = "/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public GameRoomResponse searchGameRoom(@PathVariable Long id) {
        return GameRoomResponse.toDto(gameRoomService.getOne(id));
    }

    /**
     * 새로운 게임방 생성 요쳥을 처리한다.
     * @param gameRoomCreateRequest 게임방 생성시 필요한 정보
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createGameRoom(@RequestBody GameRoomCreateRequest gameRoomCreateRequest) {
        gameRoomService.create(gameRoomCreateRequest.getName(), gameRoomCreateRequest.getCapacity());
    }

    @PutMapping(value = "/{id}")
    public void updateGameRoom(@PathVariable Long id, @RequestBody GameRoomUpdateRequest gameRoomUpdateRequest) throws IllegalAccessException {
        gameRoomService.update(id, gameRoomUpdateRequest.getName(), gameRoomUpdateRequest.getCapacity());
    }

    /**
     * 특정 id를 가진 게임방을 삭제하는 요청을 처리한다.
     * @param id 삭제하고 싶은 게임방의 고유 식별자
     */
    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteGameRoom(@PathVariable Long id) {
        gameRoomService.delete(id);
    }
}
