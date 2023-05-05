package com.semoss.agricola.GameRoomCommunication.service;

import com.semoss.agricola.GameRoomCommunication.domain.User;

public interface CommunicationService {
    public User participate(Long gameRoomId, String username);

    public User exit(Long gameRoomId, Long userId);
    public void start(Long gameRoomId);
}
