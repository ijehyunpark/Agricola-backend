package com.semoss.agricola.repository;

import com.semoss.agricola.domain.GameRoom;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class MemoryGameRoomRepository implements GameRoomRepository {

    private final List<GameRoom> gameRoomList = new ArrayList<>();
    @Override
    public Optional<GameRoom> findById(Long id) {
        return gameRoomList.stream()
                .filter(gameRoom -> gameRoom.getId() == id)
                .findAny();
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
        gameRoomList.removeIf(gameRoom -> gameRoom.getId() == id);
    }
}
