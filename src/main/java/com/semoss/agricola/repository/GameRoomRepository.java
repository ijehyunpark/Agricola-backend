package com.semoss.agricola.repository;

import com.semoss.agricola.domain.GameRoom;

import java.util.List;
import java.util.Optional;

public interface GameRoomRepository {
    Optional<GameRoom> findById(Long id);
    List<GameRoom> findAll();
    void insert(GameRoom gameRoom);
    void deleteById(Long id);
}
