package com.semoss.agricola.GameRoom.domain;

import com.semoss.agricola.GameRoomCommunication.domain.User;

import java.util.List;

public interface Game {
    public List<User> getUser();
}
