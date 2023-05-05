package com.semoss.agricola.GameRoom.Service;

import com.semoss.agricola.GameRoom.domain.GameRoom;

import java.util.List;

public interface GameRoomService {
    public List<GameRoom> getAll();
    public GameRoom getOne(Long id);
    public void create(String name, int capacity);
    public void update(Long id, String name, Integer capacity) throws IllegalAccessException;
    public void delete(Long id);
}
