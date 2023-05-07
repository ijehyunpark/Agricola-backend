package com.semoss.agricola.GamePlay.service;

public interface AgricolaService {
    public void start(Long gameRoomId);
    public void playerAction(Long gameRoomId, Long userId, Object userActions);
    public void finish(Long gameRoomId);
}
