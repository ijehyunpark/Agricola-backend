package com.semoss.agricola.GameRoomCommunication.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class GreetingDto {
    private String username;
    private Long id;
}
