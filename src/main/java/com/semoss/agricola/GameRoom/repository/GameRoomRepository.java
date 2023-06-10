package com.semoss.agricola.GameRoom.repository;

import com.semoss.agricola.GameRoom.domain.GameRoom;

import java.util.List;
import java.util.Optional;

public interface GameRoomRepository {
    Optional<GameRoom> findById(Long id);
    List<GameRoom> findAll();
    void insert(GameRoom gameRoom);
    void deleteById(Long id);
}
