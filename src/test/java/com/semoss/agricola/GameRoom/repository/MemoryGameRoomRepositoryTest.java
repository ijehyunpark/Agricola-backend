package com.semoss.agricola.GameRoom.repository;

import com.semoss.agricola.GameRoom.domain.GameRoom;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class MemoryGameRoomRepositoryTest {

    @Autowired
    private GameRoomRepository gameRoomRepository;

    @BeforeEach
    public void setUp() {
    }

    @Test
    void findByIdTest() {
        // given
        GameRoom gameRoom1 = GameRoom.builder().name("Game Room 1").capacity(4).build();
        GameRoom gameRoom2 = GameRoom.builder().name("Game Room 2").capacity(4).build();
        gameRoomRepository.insert(gameRoom1);
        gameRoomRepository.insert(gameRoom2);

        // when
        Optional<GameRoom> optionalGameRoom = gameRoomRepository.findById(gameRoom1.getId());

        // then
        assertTrue(optionalGameRoom.isPresent());
        assertEquals(gameRoom1.getName(), optionalGameRoom.get().getName());
        assertEquals(gameRoom1.getCapacity(), optionalGameRoom.get().getCapacity());
    }

    @Test
    public void testFindAll() {
        // given
        GameRoom gameRoom1 = GameRoom.builder().name("Game Room 1").capacity(4).build();
        GameRoom gameRoom2 = GameRoom.builder().name("Game Room 2").capacity(4).build();
        int beforeSize = gameRoomRepository.findAll().size();
        gameRoomRepository.insert(gameRoom1);
        gameRoomRepository.insert(gameRoom2);

        // when
        List<GameRoom> gameRooms = gameRoomRepository.findAll();

        // then
        assertEquals(beforeSize + 2, gameRooms.size());
        assertEquals("Game Room 1", gameRooms.get(beforeSize).getName());
        assertEquals("Game Room 2", gameRooms.get(beforeSize + 1).getName());
    }

    @Test
    public void testInsert() {
        // given
        GameRoom gameRoom = GameRoom.builder().name("Game Room 3").capacity(4).build();
        int beforeSize = gameRoomRepository.findAll().size();

        // when
        gameRoomRepository.insert(gameRoom);

        // then
        List<GameRoom> gameRooms = gameRoomRepository.findAll();
        assertEquals(beforeSize + 1, gameRooms.size());
        assertEquals("Game Room 3", gameRooms.get(beforeSize).getName());
    }


    @Test
    public void testDeleteById() {
        // given
        GameRoom gameRoom = GameRoom.builder().name("Game Room 4").capacity(4).build();
        gameRoomRepository.insert(gameRoom);
        Long id = gameRoom.getId();
        int beforeSize = gameRoomRepository.findAll().size();

        // when
        gameRoomRepository.deleteById(id);

        // then
        List<GameRoom> gameRooms = gameRoomRepository.findAll();
        assertEquals(beforeSize - 1, gameRooms.size());
    }
}
