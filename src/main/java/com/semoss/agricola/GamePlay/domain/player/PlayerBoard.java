package com.semoss.agricola.GamePlay.domain.player;

import com.semoss.agricola.GamePlay.domain.resource.ResourceStruct;
import com.semoss.agricola.GamePlay.domain.resource.ResourceType;
import jdk.jshell.spi.ExecutionControl;
import lombok.Builder;
import lombok.Getter;

import java.util.*;
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
    private FieldType[][] fieldStatus = new FieldType[3][5];
    private Field[][] fields = new Field[3][5];
    private boolean[][] colFence = new boolean[3][6];
    private boolean[][] rowFence = new boolean[4][5];
    private ResourceStruct[] moveAnimalArr = new ResourceStruct[3];

    @Builder
    public PlayerBoard() {
        //초기 나무집 설정
        roomType = FieldType.WOOD;

        //필드 초기화
        for (int i=0;i< fieldStatus.length;i++){
            Arrays.fill(fieldStatus[i],FieldType.EMPTY);
        }

        // 애완 동물 나무집
        Room petRoom = Room.builder()
                .isPetRoom(true)
                .build();
        petRoom.addResident(ResidentType.ADULT);
        this.fields[1][0] = petRoom;
        fieldStatus[1][0] = FieldType.ROOM;

        // 일반 나무집
        Room room = Room.builder()
                .isPetRoom(false)
                .build();
        room.addResident(ResidentType.ADULT);
        this.fields[2][0] = room;
        fieldStatus[2][0] = FieldType.ROOM;

        this.roomCount = 2;

        moveAnimalArr[0] = ResourceStruct.builder().resource(ResourceType.SHEEP).count(0).build();
        moveAnimalArr[1] = ResourceStruct.builder().resource(ResourceType.WILD_BOAR).count(0).build();
        moveAnimalArr[2] = ResourceStruct.builder().resource(ResourceType.CATTLE).count(0).build();
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

    /**
     * 설치가능한 필드 위치
     * @param fieldType 확인할 필드
     * @return 설치가능한 위치를 true 로 나타낸 2차원 필드크기 배열
     */
    protected boolean[][] availableFieldPos(FieldType fieldType){
        boolean[][] ifFirstField = new boolean[fields.length][fields[0].length];
        boolean[][] ifNthField = new boolean[fields.length][fields[0].length];
        int i,j;

        for (i=0;i<fields.length;i++) {
            for (j = 0; j < fields[0].length; j++) {
                if (fieldStatus[i][j] == FieldType.EMPTY) ifFirstField[i][j] = true;
            }
        }

        if (fieldType == FieldType.STABLE) return ifFirstField;

        boolean isIt = false;
        for (i=0;i<fields.length;i++){
            for (j=0;j<fields[0].length;j++){
                if (fieldStatus[i][j] == fieldType){
                    isIt = true;
                    if (i != 0) ifNthField[i-1][j] = fieldStatus[i-1][j] == FieldType.EMPTY;
                    if (i != fields.length-1) ifNthField[i+1][j] = fieldStatus[i+1][j] == FieldType.EMPTY;
                    if (j != 0) ifNthField[i][j-1] = fieldStatus[i][j-1] == FieldType.EMPTY;
                    if (j != fields[0].length-1) ifNthField[i][j+1] = fieldStatus[i][j+1] == FieldType.EMPTY;
                }
            }
        }
        if (isIt) {
            return ifNthField;
        }
        return ifFirstField;
    }

    /**
     * 개인보드에 필드를 설치 - 설치 명령 전 available함수로 설치가능한 위치를 확인한 후 가능한 위치만 입력받도록 함.
     * @param y row
     * @param x col
     * @param fieldType 설치하고자하는 필드
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
                fields[y][x] = Barn.builder()
                        .fieldType(FieldType.STABLE)
                        .build();
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
        fieldStatus[y][x] = fieldType;
    }

    /**
     * 현재 울타리를 설치할 수 있는 위치를 확인합니다.
     * @return [row : boolean[][], col : boolean[][]]
     */
    protected boolean[][][] availableFencePos(){
        boolean[][] row = new boolean[4][5];
        boolean[][] col = new boolean[3][6];
        int i,j;

        boolean nowStatus;
        for (i=0;i<fieldStatus.length;i++) {
            for (j=0;j<fieldStatus[0].length;j++) {
                nowStatus = fieldStatus[i][j] == FieldType.EMPTY || fieldStatus[i][j] == FieldType.BARN || fieldStatus[i][j] == FieldType.STABLE;
                row[i + 1][j] = !rowFence[i + 1][j] &&
                        (i != fieldStatus.length - 1 &&
                                (fieldStatus[i + 1][j] == FieldType.EMPTY || fieldStatus[i + 1][j] == FieldType.BARN || fieldStatus[i + 1][j] == FieldType.STABLE ||
                                        nowStatus));
                col[i][j + 1] = !colFence[i][j + 1] &&
                        (j != fieldStatus[0].length - 1 &&
                                (fieldStatus[i][j + 1] == FieldType.EMPTY || fieldStatus[i][j + 1] == FieldType.BARN || fieldStatus[i][j + 1] == FieldType.STABLE ||
                                        nowStatus));
            }
        }
        for (j=0;j<fieldStatus[0].length;j++) {
            row[0][j] = !rowFence[0][j] &&
                    (fieldStatus[0][j] == FieldType.EMPTY || fieldStatus[0][j] == FieldType.BARN || fieldStatus[0][j] == FieldType.STABLE);
            row[fieldStatus.length][j] = !rowFence[fieldStatus.length][j] &&
                    (fieldStatus[fieldStatus.length-1][j] == FieldType.EMPTY || fieldStatus[fieldStatus.length - 1][j] == FieldType.BARN || fieldStatus[fieldStatus.length - 1][j] == FieldType.STABLE);
        }
        for (i=0;i<fieldStatus.length;i++) {
            col[i][0] = !colFence[i][0] &&
                    (fieldStatus[i][0] == FieldType.EMPTY || fieldStatus[i][0] == FieldType.BARN || fieldStatus[i][0] == FieldType.STABLE);
            col[i][fieldStatus[0].length] = !colFence[i][fieldStatus[0].length] &&
                    (fieldStatus[i][fieldStatus[0].length-1] == FieldType.EMPTY || fieldStatus[i][fieldStatus[0].length-1] == FieldType.BARN || fieldStatus[i][fieldStatus[0].length-1] == FieldType.STABLE);
        }
        return new boolean[][][]{row,col};
    }

    /**
     * 울타리 적합성을 확인하고 나서 울타리 설치
     * @param rowPos [ [i,j], [i,j], ... ]
     * @param colPos [ [i,j], [i,j], ... ]
     * @return 설치하려는 위치에 이미 울타리가 있는 경우, 울타리가 적합하지 않은 경우 false
     */
    protected boolean buildFence(int[][] rowPos, int[][] colPos){
        boolean[][] tmpRow = rowFence.clone();
        boolean[][] tmpCol = colFence.clone();
        int i;
        int j;
        // 이미 설치가 되어있는 경우 return false
        for (i=0;i<rowPos.length;i++){
            if (tmpRow[rowPos[i][0]][rowPos[i][1]]) return false;
            tmpRow[rowPos[i][0]][rowPos[i][1]] = true;
        }
        for (i=0;i<colPos.length;i++){
            if (tmpCol[colPos[i][0]][colPos[i][1]]) return false;
            tmpCol[colPos[i][0]][colPos[i][1]] = true;
        }
        // 설치된 울타리 적합성 검사
        boolean[][] check = new boolean[fieldStatus.length][fieldStatus[0].length];//false
        boolean[][] checkCol = new boolean[tmpCol.length][tmpCol[0].length];
        Stack<int[]> explorer = new Stack<>();
        Stack<int[]> buildPos = new Stack<>();
        int fieldRow = fieldStatus.length;
        int fieldCol = fieldStatus[0].length;
        int[] pos = new int[2];
        for (i=0;i<tmpRow.length-1;i++){
            for (j=0;j<tmpRow[0].length;j++){
                if(tmpRow[i][j] && !check[i][j] && (fieldStatus[i][j] == FieldType.EMPTY || fieldStatus[i][j] == FieldType.BARN || fieldStatus[i][j] == FieldType.STABLE)){
                    //조건 만족시 탐색시작
                    pos[0] = i; pos[1] = j;
                    while(true) {
                        check[pos[0]][pos[1]] = true;
                        // 오른쪽
                        if (pos[1] != fieldCol-1 && !tmpCol[pos[0]][pos[1]+1] && !check[pos[0]][pos[1]+1]){
                            explorer.push(pos.clone());
                            pos[1] += 1;
                        }
                        // 아래
                        else if (pos[0] != fieldRow-1 && !tmpRow[pos[0]+1][pos[1]] && !check[pos[0]+1][pos[1]]){
                            explorer.push(pos.clone());
                            pos[0] += 1;
                        }
                        // 왼쪽
                        else if (pos[1] != 0 && !tmpCol[pos[0]][pos[1]] && !check[pos[0]][pos[1]-1]){
                            explorer.push(pos.clone());
                            pos[1] -= 1;
                        }
                        // 위
                        else if (pos[0] != 0 && !tmpRow[pos[0]][pos[1]] && !check[pos[0]-1][pos[1]]){
                            explorer.push(pos.clone());
                            pos[0] -= 1;
                        }
                        else if (pos[0] == i && pos[1] == j){
                            buildPos.push(pos.clone());
                            break;
                        }
                        else if (explorer.empty()) return false;
                        else if ((pos[0]==0 && !tmpRow[pos[0]][pos[1]]) || (pos[1]==0 && !tmpCol[pos[0]][pos[1]]) ||
                                (pos[0]==check.length-1 && !tmpRow[pos[0]+1][pos[1]]) || (pos[1]==check[0].length-1 && !tmpCol[pos[0]][pos[1]+1])) {
                            explorer.removeAllElements();
                            break;
                        }
                        else {
                            buildPos.push(pos.clone());
                            pos = explorer.pop();
                        }
                    }
                    while (!buildPos.empty()){
                        pos = buildPos.pop();
                        checkCol[pos[0]][pos[1]] = tmpCol[pos[0]][pos[1]];
                        checkCol[pos[0]][pos[1]+1] = tmpCol[pos[0]][pos[1]+1];
                    }
                    check[i][j] = true;
                }
            }
        }
        if (!Arrays.deepEquals(checkCol,tmpCol)) return false;

        // 울타리 설치 적용
        rowFence = tmpRow.clone();
        colFence = tmpCol.clone();

        buildPos = new Stack<>();
        explorer = new Stack<>();
        check = new boolean[fieldStatus.length][fieldStatus[0].length];
        int stableNum = 0;

        for (i=0;i<tmpRow.length-1;i++){
            for (j=0;j<tmpRow[0].length;j++){
                if(tmpRow[i][j] && !check[i][j] && (fieldStatus[i][j] == FieldType.EMPTY || fieldStatus[i][j] == FieldType.BARN || fieldStatus[i][j] == FieldType.STABLE)){
                    //조건 만족시 탐색시작
                    pos[0] = i; pos[1] = j;
                    while(true) {
                        check[pos[0]][pos[1]] = true;
                        // 오른쪽
                        if (pos[1] != fieldCol-1 && !tmpCol[pos[0]][pos[1]+1] && !check[pos[0]][pos[1]+1]){
                            explorer.push(pos.clone());
                            pos[1] += 1;
                        }
                        // 아래
                        else if (pos[0] != fieldRow-1 && !tmpRow[pos[0]+1][pos[1]] && !check[pos[0]+1][pos[1]]){
                            explorer.push(pos.clone());
                            pos[0] += 1;
                        }
                        // 왼쪽
                        else if (pos[1] != 0 && !tmpCol[pos[0]][pos[1]] && !check[pos[0]][pos[1]-1]){
                            explorer.push(pos.clone());
                            pos[1] -= 1;
                        }
                        // 위
                        else if (pos[0] != 0 && !tmpRow[pos[0]][pos[1]] && !check[pos[0]-1][pos[1]]){
                            explorer.push(pos.clone());
                            pos[0] -= 1;
                        }
                        else if ((pos[0]==0 && !tmpRow[pos[0]][pos[1]]) || (pos[1]==0 && !tmpCol[pos[0]][pos[1]]) ||
                                (pos[0]==check.length-1 && !tmpRow[pos[0]+1][pos[1]]) || (pos[1]==check[0].length-1 && !tmpCol[pos[0]][pos[1]+1])) {
                            explorer.removeAllElements();
                            break;
                        }
                        else if (pos[0] == i && pos[1] == j){
                            buildPos.push(pos.clone());
                            break;
                        }
                        else if (explorer.empty()) return false;
                        else {
                            if (fieldStatus[pos[0]][pos[1]] != FieldType.EMPTY && ((Barn)(fields[pos[0]][pos[1]])).isStable()) stableNum++;
                            buildPos.push(pos.clone());
                            pos = explorer.pop();
                        }
                    }
                    // 헛간 지정
                    while (!buildPos.empty()){
                        pos = buildPos.pop();
                        if (fieldStatus[pos[0]][pos[1]] == FieldType.EMPTY) {
                            fieldStatus[pos[0]][pos[1]] = FieldType.BARN;
                            fields[pos[0]][pos[1]] = Barn.builder()
                                    .fieldType(FieldType.BARN)
                                    .build();
                        }
                        ((Barn)fields[pos[0]][pos[1]]).setCapacity(stableNum);
                    }
                }
            }
        }
        return true;
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

    /**
     * 선택한 동물이 있는 위치와 마리 수 반환
     * @param resourceType 동물 타입
     * @return 내용물이 [row, col, count]인 list
     */
    protected List<int[]> animalPos(ResourceType resourceType){
        Barn barn;
        List<int[]> pos = new ArrayList<>();
        // 애완동물 집 확인
        if (((Room)fields[1][0]).getResource().getResource() == resourceType) pos.add(new int[]{1,0,1});

        for (int i=0;i<fields.length;i++){
            for (int j=0;j< fields[0].length;j++){
                if (fieldStatus[i][j] == FieldType.BARN || fieldStatus[i][j] == FieldType.STABLE){
                    barn = (Barn)fields[i][j];
                    if(barn.getAnimal().getResource() == resourceType) pos.add(new int[]{i,j, barn.getAnimal().getCount()});
                }
            }
        }
        return pos;
    }

    /**
     * 동물을 제거합니다.
     * @param row row
     * @param col col
     * @param animalNum 제거할 동물 수
     * @return 제거된 동물 수
     */
    protected int removeAnimal(int row, int col, int animalNum){
        if (fieldStatus[row][col] != FieldType.BARN && fieldStatus[row][col] != FieldType.STABLE) return 0;
        ResourceType animalType = ((Barn)fields[row][col]).getAnimal().getResource();
        int num = ((Barn)fields[row][col]).removeAnimal(animalNum);
        moveAnimalArr[animalType.getValue()-9].addResource(num);
        return num;
    }

    /**
     * 해당 칸의 모든 동물을 제거합니다.
     * @param row row
     * @param col col
     * @return 제거된 동물 수
     */
    protected int removeALLAnimals(int row, int col){
        if (fieldStatus[row][col] != FieldType.BARN && fieldStatus[row][col] != FieldType.STABLE) return 0;
        ResourceType animalType = ((Barn)fields[row][col]).getAnimal().getResource();
        int num = ((Barn)fields[row][col]).removeAllAnimals();
        moveAnimalArr[animalType.getValue()-9].addResource(num);
        return num;
    }

    /**
     * 이동 스택에 쌓아둔 동물들을 특정 위치로 재배치하는 함수
     * @param row row
     * @param col col
     * @param animalType 추가할 동물의 타입
     * @param animalNum 추가할 동물 수
     * @return 이동한 동물 수(수용가능한 동물 양에 따라 결과값이 달라짐)
     */
    protected int addRemovedAnimal(int row, int col, ResourceType animalType, int animalNum){
        if (fieldStatus[row][col] != FieldType.BARN && fieldStatus[row][col] != FieldType.STABLE) return 0;
        animalNum = Integer.min(animalNum,moveAnimalArr[animalType.getValue()-9].getCount());
        int num = ((Barn)fields[row][col]).addAnimal(animalType, animalNum);
        moveAnimalArr[animalType.getValue()-9].subResource(num);
        return num;
    }

    /**
     * 행동칸이나 수확행동을 통해 동물을 추가 하였을때 자동으로 배치하는 함수
     * @param animalType 동물 타입
     * @param animalNum 동물 수
     * @return 행동 성공 여부
     */
    protected boolean addAnimal(ResourceType animalType, int animalNum){
        Barn barn;
        for (int i=0;i<fields.length;i++){
            for (int j=0;j< fields[0].length;j++){
                if (fieldStatus[i][j] == FieldType.BARN || fieldStatus[i][j] == FieldType.STABLE){
                    barn = (Barn)fields[i][j];
                    animalNum -= barn.addAnimal(animalType,animalNum);
                }
                if (animalNum == 0) return true;
            }
        }
        if (animalNum == 1 && (((Room)fields[1][0]).getPet() == animalType || ((Room)fields[1][0]).getPet() == ResourceType.EMPTY)){
            return ((Room)fields[1][0]).addPet(animalType);
        }

        return false;
    }

    /** TODO
     * 비워둔 동물들 자동으로 다시 채우기(힘들면 전부 삭제), 비워둔 동물을 저장해둔 Map 초기화
     */

    /**
     * 보드에 있는 모든 동물 제거
     */
    protected void removeAllBarn(){
        Barn barn;
        ResourceType animalType;
        int num;
        for (int i=0;i<fields.length;i++){
            for (int j=0;j< fields[0].length;j++){
                if (fieldStatus[i][j] == FieldType.BARN || fieldStatus[i][j] == FieldType.STABLE){
                    barn = (Barn)fields[i][j];
                    animalType = barn.getAnimal().getResource();
                    num = barn.removeAllAnimals();
                    moveAnimalArr[animalType.getValue()-9].addResource(num);
                }
            }
        }
        //애완동물
        animalType = ((Room)fields[1][0]).removePet();
        if(animalType != ResourceType.EMPTY) moveAnimalArr[animalType.getValue()-9].addResource(1);
    }

    /**
     * 필드에서 임시적으로 제거해둔 동물 배열을 초기화 (동물 삭제)
     */
    protected void initMoveAnimalArr(){
        for (ResourceStruct resourceStruct : moveAnimalArr) {
            resourceStruct.setCount(0);
        }
    }


    /**
     * test 함수
     */
    public void printField(){
        for (int i=0;i<fieldStatus.length;i++){
            System.out.println(Arrays.toString(fieldStatus[i]));
        }
    }
}
