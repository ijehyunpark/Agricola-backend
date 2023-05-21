package com.semoss.agricola.GamePlay.service;

import com.semoss.agricola.GamePlay.domain.resource.ResourceStruct;
import com.semoss.agricola.GamePlay.dto.AgricolaActionRequest;

import java.util.List;

public interface AgricolaService {
    void start(Long gameRoomId);
    void playAction(Long gameRoomId, Long eventId, List<AgricolaActionRequest.ActionFormat> acts);
    void playExchange(Long gameRoomId, String improvementId, ResourceStruct resource);
    void finish(Long gameRoomId);
    boolean validatePlayer(Long gameRoomId, Object userId);
}
