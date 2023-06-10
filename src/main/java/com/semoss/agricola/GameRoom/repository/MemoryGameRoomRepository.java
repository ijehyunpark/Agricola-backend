package com.semoss.agricola.GameRoom.repository;

import com.semoss.agricola.GameRoom.domain.GameRoom;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * ArrayList로 구현된 간단한 gameRoom 저장소
 */
@Repository
public class MemoryGameRoomRepository implements GameRoomRepository {
    private final List<GameRoom> gameRoomList = new ArrayList<>();
    @Override
    public Optional<GameRoom> findById(Long id) {
        return gameRoomList.stream()
                .filter(gameRoom -> gameRoom.getId().equals(id))
                .findFirst();
    }

    @Override
    public List<GameRoom> findAll() {
        return gameRoomList;
    }

    @Override
    public void insert(GameRoom gameRoom) {
        gameRoomList.add(gameRoom);
    }

    @Override
    public void deleteById(Long id) {
        gameRoomList.removeIf(gameRoom -> gameRoom.getId().equals(id));
    }
}
