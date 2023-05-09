package com.semoss.agricola.GamePlay.service;

public interface AgricolaService {
    public void start(Long gameRoomId);
    public void playAction(Long gameRoomId, Object userActions);
    public void playExchange(Long gameRoomId, Object userActions);
    public void finish(Long gameRoomId);
    boolean validatePlayer(Long gameRoomId, Object userId);
}
