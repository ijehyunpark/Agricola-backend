package com.semoss.agricola.GameRoom.dto;

import com.semoss.agricola.GameRoom.domain.GameRoom;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GameRoomResponse {
    private final Long id;
    private final String name;
    private final int capacity;
    private final int participantNumber;

    public static GameRoomResponse toDto(GameRoom gameRoom){
        return GameRoomResponse.builder()
                .id(gameRoom.getId())
                .name(gameRoom.getName())
                .capacity(gameRoom.getCapacity())
                .participantNumber(gameRoom.getParticipants().size())
                .build();
    }
}
