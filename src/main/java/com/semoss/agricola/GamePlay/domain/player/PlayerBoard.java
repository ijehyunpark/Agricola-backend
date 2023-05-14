package com.semoss.agricola.GamePlay.domain.player;

import com.semoss.agricola.GamePlay.domain.resource.ResourceStruct;
import jdk.jshell.spi.ExecutionControl;
import lombok.Builder;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

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
        return Arrays.stream(fields)
                .flatMap(Arrays::stream)
                .filter(field -> field instanceof Room)
                .allMatch(field -> ((Room) field).isCompletedPlayed());
    }

    /**
     * 플레이여부 초기화
     */
    protected void initPlayed() {
        Arrays.stream(fields)
                .flatMap(Arrays::stream)
                .filter(field -> field instanceof Room)
                .map(field -> (Room) field)
                .forEach(Room::initPlayed);
    }

    /**
     * 아이 성장
     */
    protected void growUpChild() {
        Arrays.stream(fields)
                .flatMap(Arrays::stream)
                .filter(field -> field instanceof Room)
                .map(field -> (Room)field)
                .forEach(Room::growUpChild);
    }

    /**
     * 모든 밭에서 곡식 및 채소를 수확한다.
     */
    protected List<ResourceStruct> harvest() {
        return Arrays.stream(fields)
                .flatMap(Arrays::stream)
                .filter(field -> field instanceof Farm)
                .map(field -> ((Farm) field).harvest())
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
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
        return Arrays.stream(fields)
                .flatMap(Arrays::stream)
                .filter(field -> field instanceof Room)
                .mapToInt(field -> ((Room) field).calculateFoodNeeds())
                .sum();
    }

    /**
     * 빈 방 확인
     * @return 만약 빈 방이 존재할 경우, true를 반환한다.
     */
    protected boolean hasEmptyRoom() {
        return Arrays.stream(fields)
                .flatMap(Arrays::stream)
                .filter(field -> field instanceof Room)
                .map(room -> (Room) room)
                .anyMatch(Room::isEmptyRoom);
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
    protected void addChild() {
        // 빈 방에 우선 추가
        Optional<Room> emptyRoom = Arrays.stream(fields)
                .flatMap(Arrays::stream)
                .filter(field -> field instanceof Room && ((Room) field).isEmptyRoom())
                .map(field -> (Room) field)
                .findFirst();

        if (emptyRoom.isPresent()) {
            emptyRoom.get().addResident(ResidentType.CHILD);
            return;
        }

        // 없으면 아무 방에 추가
        Arrays.stream(fields)
                .flatMap(Arrays::stream)
                .filter(field -> field instanceof Room)
                .map(field -> (Room) field)
                .findFirst()
                .ifPresent(room -> room.addResident(ResidentType.CHILD));
    }

    /**
     * 가족 개수 반환
     * @return
     */
    protected int getFamilyCount(){
        return Arrays.stream(fields)
                .flatMap(Arrays::stream)
                .filter(Room.class::isInstance)
                .mapToInt(field -> ((Room) field).getResidentNumber())
                .sum();
    }
}
