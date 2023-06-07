package com.semoss.agricola.GamePlay.service;

import com.semoss.agricola.GamePlay.domain.resource.AnimalType;
import com.semoss.agricola.GamePlay.domain.resource.ResourceType;
import com.semoss.agricola.GamePlay.dto.AgricolaActionRequest;

import java.util.List;

public interface AgricolaService {
    void start(Long gameRoomId);
    void playAction(Long gameRoomId, Long eventId, List<AgricolaActionRequest.ActionFormat> acts);
    void playExchange(Long gameRoomId, Long improvementId, ResourceType resource, int count);
    void playExchange(Long gameRoomId, Long improvementId, AnimalType resource, int count);
    void playRelocation(Long gameRoomId, Integer y, Integer x, Integer newY, Integer newX, Integer count);
    void playRelocation(Long gameRoomId, AnimalType animalType, Integer newY, Integer newX, Integer count);
    void finish(Long gameRoomId);
    boolean validatePlayer(Long gameRoomId, Object userId);

}
