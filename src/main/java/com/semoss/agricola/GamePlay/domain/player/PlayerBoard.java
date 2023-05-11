package com.semoss.agricola.GamePlay.domain.player;

import com.semoss.agricola.GamePlay.domain.resource.ResourceStruct;
import jdk.jshell.spi.ExecutionControl;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * 아그리콜라 플레이어 게임 보드
 */
@Getter
public class PlayerBoard {
    private int roomCount;
    private FieldType roomType;
//    private int farmCount;
//    private int fenceCount;
//    private int stableCount;
//    private int[][] fieldStatus = new int[3][5];
    private Field[][] fields = new Field[3][5];
//    private int[][] fence = new int[4][6];

    @Builder
    public PlayerBoard() {
        //초기 나무집 설정
        roomType = FieldType.WOOD;

        // 애완 동물 나무집
        Room petRoom = Room.builder()
                .isPetRoom(true)
                .build();
        petRoom.addResident(ResidentType.ADULT);
        this.fields[1][0] = petRoom;

        // 일반 나무집
        Room room = Room.builder()
                .isPetRoom(false)
                .build();
        room.addResident(ResidentType.ADULT);
        this.fields[2][0] = room;

        this.roomCount = 2;
    }

    /**
     * 방 업그레이드
     */
    public void upgradeRoom() {
        // 방을 한단계 업그레이드 한다.
        if (roomType == FieldType.WOOD)
            roomType = FieldType.CLAY;
        else if (roomType == FieldType.CLAY)
            roomType = FieldType.STONE;
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

    /**
     * 모든 밭에서 곡식 및 채소를 수확한다.
     */
    protected List<ResourceStruct> harvest() {
        List<ResourceStruct> outputs = new ArrayList<>();
        for (int i = 0; i < 3; i++){
            for(int j = 0; j < 5; j++){
                Field field = fields[i][j];
                if (field instanceof Farm) {
                    ResourceStruct output = ((Farm) field).harvest();
                    if(output != null)
                        outputs.add(output);
                }
            }
        }
        return outputs;
    }

    /**
     * 동물 번식시키기
     */
    protected void breeding() throws ExecutionControl.NotImplementedException {
        throw new ExecutionControl.NotImplementedException("미구현");
    }

    /**
     * 필요한 음식 토큰 개수 계산
     */
    protected int calculateFoodNeeds() {
        int result = 0;
        for (int i = 0; i < 3; i++){
            for(int j = 0; j < 5; j++){
                Field field = fields[i][j];
                if (field instanceof Room) {
                    result += ((Room) field).calculateFoodNeeds();
                }
            }
        }
        return result;
    }

    /**
     * 빈 방 확인
     * @return 만약 빈 방이 존재할 경우, true를 반환한다.
     */
    protected boolean hasEmptyRoom() {
        for (int i = 0; i < 3; i++){
            for(int j = 0; j < 5; j++){
                Field field = fields[i][j];
                if (field instanceof Room) {
                    if(((Room) field).isEmptyRoom())
                        return true;
                }
            }
        }
        return false;
    }

    protected void buildField(int y, int x, FieldType fieldType) {
        if (fields[y][x] != null)
            throw new RuntimeException("이미 건설 되어 있습니다.");
        switch (fieldType){
            case FARM -> {
                fields[y][x] = new Farm();
            }
            case ROOM -> {
                fields[y][x] = Room.builder()
                        .isPetRoom(false)
                        .build();
            }
            case STABLE -> {
                // TODO
                throw new RuntimeException("friends");
            }
            case FENCE -> {
                // TODO
                throw new RuntimeException("world");
            }
            default -> {
                // TODO
                throw new RuntimeException("hello");
            }
        }
    }

    /**
     * 아기를 입양한다.
     */
    protected void addChard() {
        // 빈 방에 우선 추가
        for (int i = 0; i < 3; i++){
            for(int j = 0; j < 5; j++){
                Field field = fields[i][j];
                if (field instanceof Room) {
                    if(((Room) field).isEmptyRoom()){
                        ((Room) field).addResident(ResidentType.CHILD);
                        return;
                    }
                }
            }
        }

        // 없으면 아무 방에 추가
        for (int i = 0; i < 3; i++){
            for(int j = 0; j < 5; j++){
                Field field = fields[i][j];
                if (field instanceof Room) {
                    ((Room) field).addResident(ResidentType.CHILD);
                    return;
                }
            }
        }
    }
}
