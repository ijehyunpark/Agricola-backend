package com.semoss.agricola.dto;

import com.semoss.agricola.domain.GameRoom;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class GameRoomResponse {
    private Long id;
    private String name;
    private int capacity;

    public static GameRoomResponse toDto(GameRoom gameRoom) {
        return GameRoomResponse.builder()
                .id(gameRoom.getId())
                .name(gameRoom.getName())
                .capacity(gameRoom.getCapacity())
                .build();
    }
}
