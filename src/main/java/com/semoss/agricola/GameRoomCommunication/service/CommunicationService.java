package com.semoss.agricola.GameRoomCommunication.service;

import com.semoss.agricola.GameRoomCommunication.domain.User;

public interface CommunicationService {
    User participate(Long gameRoomId, String username);

    User exit(Long gameRoomId, Long userId);
}
