package com.semoss.agricola.GameRoom.dto;

import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class GameRoomCreateRequest {
    private String name;
    private Integer capacity = 4;
}
