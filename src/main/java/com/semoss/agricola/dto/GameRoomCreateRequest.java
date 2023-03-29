package com.semoss.agricola.dto;

import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class GameRoomCreateRequest {
    private String name;
    private int capacity;
}
