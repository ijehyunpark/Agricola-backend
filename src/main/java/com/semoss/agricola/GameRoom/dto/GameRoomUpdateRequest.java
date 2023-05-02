package com.semoss.agricola.GameRoom.dto;

import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class GameRoomUpdateRequest {
    private String name;
    private Integer capacity;
}
