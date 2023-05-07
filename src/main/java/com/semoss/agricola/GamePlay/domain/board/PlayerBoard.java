package com.semoss.agricola.GamePlay.domain.board;

import com.semoss.agricola.GamePlay.domain.field.*;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;

/**
 * 아그리콜라 플레이어 게임 보드
 */
@Getter
public class PlayerBoard {
    private int roomCount;
    private int roomType;
    private int farmCount;
    private int fenceCount;
    private int stableCount;
    private int[][] fieldStatus = new int[3][5];
    private Field[][] fields = new Field[3][5];
    private int[][] fence = new int[4][6];

    @Builder
    public PlayerBoard() {
        // 애완 동물 나무집
        Room petRoom = Room.builder()
                .isPetRoom(true)
                .roomType(RoomType.WOOD)
                .build();
        petRoom.addResident(ResidentType.ADULT);
        this.fields[1][0] = petRoom;

        // 일반 나무집
        Room room = Room.builder()
                .isPetRoom(false)
                .roomType(RoomType.WOOD)
                .build();
        room.addResident(ResidentType.ADULT);
        this.fields[2][0] = room;
    }
}
