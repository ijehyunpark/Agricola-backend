package com.semoss.agricola.GamePlay.domain.player;

import com.semoss.agricola.GamePlay.domain.resource.ResourceStruct;
import lombok.Builder;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 아그리콜라 플레이어 게임 보드
 */
@Getter
public class PlayerBoard {
    private int roomCount;
    private RoomType roomType;
//    private int farmCount;
//    private int fenceCount;
//    private int stableCount;
//    private int[][] fieldStatus = new int[3][5];
    private Field[][] fields = new Field[3][5];
//    private int[][] fence = new int[4][6];

    @Builder
    public PlayerBoard() {
        //초기 나무집 설정
        roomType = RoomType.WOOD;

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
     * 방을 업그레이드한다.
     */
    public void upgradeRoom() {
        // 방을 한단계 업그레이드 한다.
        if (roomType == RoomType.WOOD)
            roomType = RoomType.CLAY;
        else if (roomType == RoomType.CLAY)
            roomType = RoomType.STONE;
    }

    /**
     * 플레이하지 않은 거주자 여부를 확인한다.
     * @return 모든 거주자가 플레이 되었을 경우 true를 반환한다.
     */
    protected boolean isCompletedPlayed() {
        return Arrays.stream(fields)
                .flatMap(Arrays::stream)
                .filter(field -> field instanceof Room)
                .allMatch(field -> ((Room) field).isCompletedPlayed());
    }

    /**
     * 플레이여부 초기화한다.
     */
    protected void initPlayed() {
        Arrays.stream(fields)
                .flatMap(Arrays::stream)
                .filter(field -> field instanceof Room)
                .map(field -> (Room) field)
                .forEach(Room::initPlayed);
    }

    /**
     * 모든 아이를 성장시킨다.
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
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
    }

    /**
     * TODO: 동물을 번식시킨다.
     */
    protected void breeding() {
        // do nothing now
    }

    /**
     * 먹여살리기 단게에서 필요한 총 음식 토큰 개수 계산한다.
     */
    protected int calculateFoodNeeds() {
        return Arrays.stream(fields)
                .flatMap(Arrays::stream)
                .filter(field -> field instanceof Room)
                .mapToInt(field -> ((Room) field).calculateFoodNeeds())
                .sum();
    }

    /**
     * 빈 방을 확인한다.
     * @return 만약 빈 방이 존재할 경우, true를 반환한다.
     */
    protected boolean hasEmptyRoom() {
        return Arrays.stream(fields)
                .flatMap(Arrays::stream)
                .filter(field -> field instanceof Room)
                .map(room -> (Room) room)
                .anyMatch(Room::isEmptyRoom);
    }

    /**
     * TODO: 빈 필드에 새로운 건물을 건설한다.
     * @param y 건설할 y축 좌표
     * @param x 건설할 x축 좌표
     * @param fieldType 건설할 건물 종류
     */
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
                // TODO: 외양간 건설 요청시
                throw new RuntimeException("friends");
            }
            case FENCE -> {
                // TODO: 울타리 건설 요청시
                throw new RuntimeException("world");
            }
            default -> {
                throw new RuntimeException("불가능한 입력입니다.");
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
     * 총 거주자 인원수를 반환한다.
     * @return 총 거주자 인원수
     */
    protected int getFamilyCount(){
        return Arrays.stream(fields)
                .flatMap(Arrays::stream)
                .filter(Room.class::isInstance)
                .mapToInt(field -> ((Room) field).getResidentNumber())
                .sum();
    }

    /**
     * 액션을 플레이한다.
     */
    public void playAction() {
        Arrays.stream(fields)
                .flatMap(Arrays::stream)
                .filter(field -> field instanceof Room)
                .map(field -> (Room) field)
                .filter(room -> !room.isCompletedPlayed())
                .findFirst()
                .orElseThrow(RuntimeException::new)
                .play();
    }
}
