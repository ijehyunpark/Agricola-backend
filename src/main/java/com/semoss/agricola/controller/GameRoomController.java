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
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<GameRoomResponse> searchAllGameRoom(){
        return gameRoomService.getGameRoomList();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void makeGameRoom(@RequestBody GameRoomCreateRequest gameRoomCreateRequest) {
        gameRoomService.createGameRoom(gameRoomCreateRequest);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteGameRoom(@PathVariable Long id) {
        gameRoomService.destoryGameRoom(id);
    }
}
