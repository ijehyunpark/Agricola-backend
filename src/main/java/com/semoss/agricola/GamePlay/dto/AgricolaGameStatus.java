package com.semoss.agricola.GamePlay.dto;

import com.semoss.agricola.GameRoom.domain.GameRoom;
import lombok.Builder;
import lombok.Getter;


@Getter
@Builder
public class AgricolaGameStatus {
    private GameRoom gameRoom;

    public static AgricolaGameStatus toDto(GameRoom gameRoom){
        return AgricolaGameStatus.builder()
                .gameRoom(gameRoom)
                .build();
    }
}
