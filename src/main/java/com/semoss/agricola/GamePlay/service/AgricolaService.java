package com.semoss.agricola.GamePlay.service;

import com.semoss.agricola.GamePlay.domain.resource.ResourceStruct;

public interface AgricolaService {
    public void start(Long gameRoomId);
    public void playAction(Long gameRoomId, Long eventId, Object acts);
    public void playExchange(Long gameRoomId, String improvementId, ResourceStruct resource, int count);
    public void finish(Long gameRoomId);
    boolean validatePlayer(Long gameRoomId, Object userId);
}
