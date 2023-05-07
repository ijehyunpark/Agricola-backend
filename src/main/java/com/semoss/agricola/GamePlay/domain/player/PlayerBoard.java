package com.semoss.agricola.GamePlay.domain.player;

import com.semoss.agricola.GamePlay.domain.player.type.ResidentType;
import com.semoss.agricola.GamePlay.domain.player.type.RoomType;
import lombok.Builder;
import lombok.Getter;

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

    /**
     * 플레이하지 않은 가족말 여부
     * @return
     */
    protected boolean isCompletedPlayed() {
        for (int i = 0; i < 3; i++){
            for(int j = 0; j < 5; j++){
                Field field = fields[i][j];
                if (field instanceof Room) {
                    if(((Room) field).isCompletedPlayed() == false)
                        return false;
                }
            }
        }
        return true;
    }

    /**
     * 플레이여부 초기화
     */
    protected void initPlayed() {
        for (int i = 0; i < 3; i++){
            for(int j = 0; j < 5; j++){
                Field field = fields[i][j];
                if (field instanceof Room) {
                    ((Room) field).initPlayed();
                }
            }
        }
    }

    /**
     * 아이 성장
     */
    protected void growUpChild() {
        for (int i = 0; i < 3; i++){
            for(int j = 0; j < 5; j++){
                Field field = fields[i][j];
                if (field instanceof Room) {
                    ((Room) field).growUpChild();
                }
            }
        }
    }
}
