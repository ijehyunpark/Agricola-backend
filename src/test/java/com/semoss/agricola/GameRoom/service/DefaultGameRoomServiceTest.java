package com.semoss.agricola.GameRoom.service;

import com.semoss.agricola.GameRoom.repository.GameRoomRepository;
import com.semoss.agricola.GameRoom.domain.GameRoom;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DefaultGameRoomServiceTest {

    @Mock
    private GameRoomRepository gameRoomRepository;

    @InjectMocks
    private DefaultGameRoomService gameRoomService;

    private GameRoom gameRoom;
    private Long gameRoomId;

    @BeforeEach
    void setUp() {
        gameRoom = GameRoom.builder()
                .name("Game Room 1")
                .capacity(4)
                .build();
        gameRoomId = gameRoom.getId();
    }

    @Test
    void testGetAll() {
        // given
        List<GameRoom> gameRooms = new ArrayList<>();
        gameRooms.add(gameRoom);
        when(gameRoomRepository.findAll()).thenReturn(gameRooms);

        // when
        List<GameRoom> result = gameRoomService.getAll();

        // then
        assertEquals(gameRooms.size(), result.size());
        assertEquals(gameRoom.getName(), result.get(0).getName());
        assertEquals(gameRoom.getCapacity(), result.get(0).getCapacity());

        verify(gameRoomRepository, times(1)).findAll();
    }

    @Test
    void testGetOne() {
        // given
        when(gameRoomRepository.findById(anyLong())).thenReturn(Optional.of(gameRoom));

        // when
        GameRoom result = gameRoomService.getOne(gameRoomId);

        // then
        assertNotNull(result);
        assertEquals(gameRoom.getName(), result.getName());
        assertEquals(gameRoom.getCapacity(), result.getCapacity());

        verify(gameRoomRepository, times(1)).findById(anyLong());
    }

    @Test
    void testGetOneThrowsExceptionWhenIdNotFound() {
        // given
        when(gameRoomRepository.findById(anyLong())).thenReturn(Optional.empty());

        // when, then
        assertThrows(NoSuchElementException.class, () -> gameRoomService.getOne(gameRoomId));

        verify(gameRoomRepository, times(1)).findById(anyLong());
    }

    @Test
    void testCreate() {
        // given
        doNothing().when(gameRoomRepository).insert(ArgumentMatchers.any(GameRoom.class));

        // when
        gameRoomService.create("Game Room 1", 4);

        // then
        verify(gameRoomRepository, times(1)).insert(ArgumentMatchers.any(GameRoom.class));
    }

    @Test
    void testUpdate() {
        // given
        when(gameRoomRepository.findById(anyLong())).thenReturn(Optional.of(gameRoom));

        // when
        gameRoomService.update(gameRoomId, "Game Room 2", 5);

        // then
        verify(gameRoomRepository, times(1)).findById(anyLong());
    }

    @Test
    void testDelete() {
        // given, when
        gameRoomService.delete(gameRoomId);

        // then
        verify(gameRoomRepository, times(1)).deleteById(anyLong());
    }
}
