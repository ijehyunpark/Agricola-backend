package com.semoss.agricola.service;

import com.semoss.agricola.domain.GameRoom;
import com.semoss.agricola.dto.GameRoomCreateRequest;
import com.semoss.agricola.dto.GameRoomResponse;
import com.semoss.agricola.repository.GameRoomRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.BDDMockito.given;


@ExtendWith(MockitoExtension.class)
class GameRoomServiceTest {

    @Spy
    @InjectMocks
    GameRoomService gameRoomService;

    @Mock
    GameRoomRepository gameRoomRepository;

    GameRoom makeTestGameRoom() {
        return GameRoom.builder()
                .name("test")
                .capacity(4)
                .build();
    }
    GameRoomCreateRequest makeTestGameRoomCreateRequest() {
        return GameRoomCreateRequest.builder()
                .name("test")
                .capacity(4)
                .build();
    }

    @Test
    void 게임방존재여부검색_성공() {
        given(gameRoomRepository.findById(10L)).willReturn(Optional.of(makeTestGameRoom()));

        Assertions.assertEquals(true, gameRoomService.existGameRoom(10L));
    }

    @Test
    void 게임방존재여부검색_실패() {
        given(gameRoomRepository.findById(10L)).willReturn(Optional.empty());

        Assertions.assertEquals(false, gameRoomService.existGameRoom(10L));
    }

    @Test
    void 게임룸검색_성공() {
        GameRoom testGameRoom = makeTestGameRoom();
        given(gameRoomRepository.findById(10L)).willReturn(Optional.of(testGameRoom));

        Assertions.assertEquals(Optional.of(testGameRoom), gameRoomService.getGameRoom(10L));

    }

    @Test
    void 게임룸검색_실패() {
        given(gameRoomRepository.findById(10L)).willReturn(Optional.empty());

        Assertions.assertEquals(Optional.empty(), gameRoomService.getGameRoom(10L));

    }

    @Test
    @Disabled
    void 유저 () {
        // nothing to do
    }

    @Test
    @Disabled
    void deleteUser() {

    }

    @Test
    void getGameRoomList() {
        GameRoom gameRoom1 = makeTestGameRoom();
        GameRoom gameRoom2 = makeTestGameRoom();
        List<GameRoom> gameRoomList = List.of(gameRoom1, gameRoom2);
        given(gameRoomRepository.findAll()).willReturn(gameRoomList);

        Assertions.assertEquals(gameRoomList, gameRoomRepository.findAll());
    }

    @Test
    void createGameRoom() {
        gameRoomService.createGameRoom(makeTestGameRoomCreateRequest());
    }

    @Test
    @Disabled
    void destroyGameRoom() {
    }
}