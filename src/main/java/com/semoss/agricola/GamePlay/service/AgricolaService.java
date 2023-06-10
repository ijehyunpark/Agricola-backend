package com.semoss.agricola.GamePlay.service;

import com.semoss.agricola.GamePlay.domain.resource.AnimalType;
import com.semoss.agricola.GamePlay.domain.resource.ResourceType;
import com.semoss.agricola.GamePlay.dto.AgricolaActionRequest;

import java.util.List;

/**
 * 아그리 콜라 게임 메인 로직
 */
public interface AgricolaService {
    // 게임 시작
    void start(Long gameRoomId);
    // 플레이어 액션 요청 수락
    void playAction(Long gameRoomId, Long eventId, List<AgricolaActionRequest.ActionFormat> acts);
    // 플레이어 교환 요청 수락
    void playExchange(Long gameRoomId, Long improvementId, ResourceType resource, int count);
    void playExchange(Long gameRoomId, Long improvementId, AnimalType resource, int count);
    // 플레이어 재배치 요청 수락
    void playRelocation(Long gameRoomId, Integer y, Integer x, Integer newY, Integer newX, Integer count);
    void playRelocation(Long gameRoomId, AnimalType animalType, Integer newY, Integer newX, Integer count);
    // 게임 종료
    void finish(Long gameRoomId);
    // 플레이어 턴 검증
    boolean validatePlayer(Long gameRoomId, Object userId);

}