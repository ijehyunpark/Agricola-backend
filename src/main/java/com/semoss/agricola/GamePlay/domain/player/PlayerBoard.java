package com.semoss.agricola.GamePlay.domain.player;

import com.semoss.agricola.GamePlay.domain.resource.AnimalStruct;
import com.semoss.agricola.GamePlay.domain.resource.ResourceStruct;
import com.semoss.agricola.GamePlay.domain.resource.ResourceType;
import com.semoss.agricola.GamePlay.exception.*;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 아그리콜라 플레이어 게임 보드
 */
@Getter
@Log4j2
public class PlayerBoard {
    private int roomCount;
    private RoomType roomType;
    private static final int FIELD_ROW = 3;
    private static final int FIELD_COL = 5;

    private Field[][] fields = new Field[FIELD_ROW][FIELD_COL];
    private boolean[][] colFence = new boolean[3][6];
    private boolean[][] rowFence = new boolean[4][5];
    private AnimalStruct[] moveAnimalArr = new AnimalStruct[3];

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

        moveAnimalArr[0] = AnimalStruct.builder().animal(AnimalType.SHEEP).count(0).build();
        moveAnimalArr[1] = AnimalStruct.builder().animal(AnimalType.WILD_BOAR).count(0).build();
        moveAnimalArr[2] = AnimalStruct.builder().animal(AnimalType.CATTLE).count(0).build();
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
                .filter(Objects::nonNull)
                .filter(field -> field.getFieldType() == FieldType.ROOM)
                .allMatch(field -> ((Room) field).isCompletedPlayed());
    }

    /**
     * 플레이여부 초기화한다.
     */
    protected void initPlayed() {
        Arrays.stream(fields)
                .flatMap(Arrays::stream)
                .filter(Objects::nonNull)
                .filter(field -> field.getFieldType() == FieldType.ROOM)
                .map(field -> (Room) field)
                .forEach(Room::initPlayed);
    }

    /**
     * 모든 아이를 성장시킨다.
     */
    protected void growUpChild() {
        Arrays.stream(fields)
                .flatMap(Arrays::stream)
                .filter(Objects::nonNull)
                .filter(field -> field.getFieldType() == FieldType.ROOM)
                .map(field -> (Room)field)
                .forEach(Room::growUpChild);
    }

    /**
     * 모든 밭에서 곡식 및 채소를 수확한다.
     */
    protected List<ResourceStruct> harvest() {
        return Arrays.stream(fields)
                .flatMap(Arrays::stream)
                .filter(Objects::nonNull)
                .filter(field -> field.getFieldType() == FieldType.BARN)
                .map(field -> ((Farm) field).harvest())
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
    }

    /**
     * TODO: 동물을 번식시킨다.
     */
    protected void breeding() {
        Map<AnimalType, Integer> animals = new HashMap<>();

        // 모든 가축 개수 계산
        Arrays.stream(fields)
                .flatMap(Arrays::stream)
                .filter(Objects::nonNull)
                .filter(field -> field.getFieldType() == FieldType.BARN)
                .map(field -> ((Barn) field).getAnimal())
                .forEach(animalStruct -> animals.put(animalStruct.getAnimal(), animalStruct.getCount()));

        Arrays.stream(fields)
                .flatMap(Arrays::stream)
                .filter(Objects::nonNull)
                .filter(field -> field.getFieldType() == FieldType.ROOM)
                .map(field -> ((Room) field))
                .filter(room -> room.getPetRoom() == null)
                .map(room -> room.getPetRoom().getAnimal())
                .forEach(animalStruct -> animals.put(animalStruct.getAnimal(), animalStruct.getCount()));


        // 가축이 2마리 이상이면 하나를 얻는다.
        for (Map.Entry<AnimalType, Integer> animal : animals.entrySet()) {
            if (animal.getValue() >= 2) {
                addAnimal(animal.getKey(), 1);
            }
        }
    }

    /**
     * 먹여살리기 단게에서 필요한 총 음식 토큰 개수 계산한다.
     */
    protected int calculateFoodNeeds() {
        return Arrays.stream(fields)
                .flatMap(Arrays::stream)
                .filter(Objects::nonNull)
                .filter(field -> field.getFieldType() == FieldType.ROOM)
                .mapToInt(field -> ((Room) field).calculateFoodNeeds())
                .sum();
    }

    /**
     * 빈 방을 확인한다.
     * @return 만약 빈 방이 존재할 경우, true를 반환한다.
     */
    protected boolean existEmptyRoom() {
        return Arrays.stream(fields)
                .flatMap(Arrays::stream)
                .filter(Objects::nonNull)
                .filter(field -> field.getFieldType() == FieldType.ROOM)
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
                if (fields[i][j] == null) ifFirstField[i][j] = true;
            }
        }

        if (fieldType == FieldType.STABLE) return ifFirstField;

        boolean isIt = false;
        for (i=0;i<fields.length;i++){
            for (j=0;j<fields[0].length;j++){
                if (fields[i][j] != null && fields[i][j].getFieldType() == fieldType){
                    isIt = true;
                    if (i != 0) ifNthField[i-1][j] = (fields[i-1][j] == null);
                    if (i != fields.length-1) ifNthField[i+1][j] = (fields[i+1][j] == null);
                    if (j != 0) ifNthField[i][j-1] = (fields[i][j-1] == null);
                    if (j != fields[0].length-1) ifNthField[i][j+1] = (fields[i][j+1] == null);
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
        if (y < 0 || y >= FIELD_ROW || x < 0 || x >= FIELD_COL) {
            throw new NotAllowRequestException("필드 위치가 부적절합니다.");
        }

        if (fields[y][x] != null){
            // 외양간이 건설 되어 있으면 마구간을 설치
            if(fields[y][x].getFieldType() == FieldType.BARN && fieldType == FieldType.STABLE) {
               ((Barn) fields[y][x]).addStable();
               return;
            }

            throw new AlreadyExistException("이미 건설 되어 있습니다.");
        }

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
                throw new NotAllowRequestException("울타리 건설은 필드에서 지원되지 않습니다/");
            }
            default -> {
                throw new ServerError();
            }
        }
    }

    protected void addStable(int row, int col){
        if (fields[row][col] == null || fields[row][col].getFieldType() != FieldType.BARN) return;
        ((Barn)fields[row][col]).addStable();
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
        for (i=0;i<fields.length;i++) {
            for (j=0;j<fields[0].length;j++) {
                nowStatus = fields[i][j] == null || fields[i][j].getFieldType() == FieldType.BARN || fields[i][j].getFieldType() == FieldType.STABLE;
                row[i + 1][j] = !rowFence[i + 1][j] &&
                        (i != fields.length - 1 &&
                                (fields[i + 1][j] == null || fields[i + 1][j].getFieldType() == FieldType.BARN || fields[i + 1][j].getFieldType() == FieldType.STABLE ||
                                        nowStatus));
                col[i][j + 1] = !colFence[i][j + 1] &&
                        (j != fields[0].length - 1 &&
                                (fields[i][j + 1] == null || fields[i][j + 1].getFieldType() == FieldType.BARN || fields[i][j + 1].getFieldType() == FieldType.STABLE ||
                                        nowStatus));
            }
        }
        for (j=0;j<fields[0].length;j++) {
            row[0][j] = !rowFence[0][j] &&
                    (fields[0][j] == null || fields[0][j].getFieldType() == FieldType.BARN || fields[0][j].getFieldType() == FieldType.STABLE);
            row[fields.length][j] = !rowFence[fields.length][j] &&
                    (fields[fields.length-1][j] == null || fields[fields.length - 1][j].getFieldType() == FieldType.BARN || fields[fields.length - 1][j].getFieldType() == FieldType.STABLE);
        }
        for (i=0;i<fields.length;i++) {
            col[i][0] = !colFence[i][0] &&
                    (fields[i][0] == null || fields[i][0].getFieldType() == FieldType.BARN || fields[i][0].getFieldType() == FieldType.STABLE);
            col[i][fields[0].length] = !colFence[i][fields[0].length] &&
                    (fields[i][fields[0].length-1] == null || fields[i][fields[0].length-1].getFieldType() == FieldType.BARN || fields[i][fields[0].length-1].getFieldType() == FieldType.STABLE);
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
        // 울타리를 설치할 수 있는 위치인지 확인
        boolean[][][] available = availableFencePos();
        boolean[][] row = available[0];
        boolean[][] col = available[1];

        for(int[] pos : rowPos){
            if (!row[pos[0]][pos[1]]) throw new IllegalRequestException("설치할 수 없는 위치입니다.");
        }

        for(int[] pos : colPos){
            if (!col[pos[0]][pos[1]]) throw new IllegalRequestException("설치할 수 없는 위치입니다.");
        }

        boolean[][] tmpRow = new boolean[rowFence.length][rowFence[0].length];
        boolean[][] tmpCol = new boolean[colFence.length][colFence[0].length];
        int i=0;
        int j=0;
        for (i=0;i< tmpRow.length;i++){
            tmpRow[i] = rowFence[i].clone();
        }
        for (i=0;i< tmpCol.length;i++){
            tmpCol[i] = colFence[i].clone();
        }
        // 이미 설치가 되어있는 경우 return false
        for (i=0;i<rowPos.length;i++){
            if (tmpRow[rowPos[i][0]][rowPos[i][1]]) throw new IllegalRequestException("설치할 수 없는 위치입니다.");
            tmpRow[rowPos[i][0]][rowPos[i][1]] = true;
        }
        for (i=0;i<colPos.length;i++){
            if (tmpCol[colPos[i][0]][colPos[i][1]]) throw new IllegalRequestException("설치할 수 없는 위치입니다.");
            tmpCol[colPos[i][0]][colPos[i][1]] = true;
        }
        // 설치된 울타리 적합성 검사
        // 모든 울타리가 붙어있는지 확인
        boolean exist = false;
        for (i=0;i<FIELD_ROW+1;i++){
            for (j=0;j<FIELD_COL;j++){
                if (tmpRow[i][j]) {
                    exist = true;
                    break;
                }
            }
            if (exist) break;
        }

        boolean[][] visitedRowFence = new boolean[tmpRow.length][tmpRow[0].length];
        boolean[][] visitedColFence = new boolean[tmpCol.length][tmpCol[0].length];
        boolean isRow = true;
        Stack<int[]> stack = new Stack<>();
        while(true){
            if(isRow) {
                visitedRowFence[i][j] = true;
                //왼쪽 위 세로
                if (i != 0 && tmpCol[i-1][j] && !visitedColFence[i-1][j]){
                    stack.push(new int[]{i,j,0});
                    i--;
                    isRow = false;
                }
                //왼쪽 아래 세로
                else if (i != FIELD_ROW && tmpCol[i][j] && !visitedColFence[i][j]){
                    stack.push(new int[]{i,j,0});
                    isRow = false;
                }
                //오른쪽 위 세로
                else if (i != 0 && j != FIELD_COL && tmpCol[i-1][j+1] && !visitedColFence[i-1][j+1]){
                    stack.push(new int[]{i,j,0});
                    i--;
                    j++;
                    isRow = false;
                }
                //오른쪽 아래 세로
                else if (i != FIELD_ROW && j != FIELD_COL && tmpCol[i][j+1] && !visitedColFence[i][j+1]){
                    stack.push(new int[]{i,j,0});
                    j++;
                    isRow = false;
                }
                //왼쪽 가로
                else if (j != 0 && tmpRow[i][j-1] && !visitedRowFence[i][j-1]){
                    stack.push(new int[]{i,j,0});
                    j--;
                }
                //오른쪽 가로
                else if (j != FIELD_COL-1 && tmpRow[i][j+1] && !visitedRowFence[i][j+1]){
                    stack.push(new int[]{i,j,0});
                    j++;
                }
                else if (stack.empty()) break;
                else {
                    int[] pos = stack.pop();
                    i = pos[0];
                    j = pos[1];
                    if (pos[2] == 1) isRow = false;
                }
            }
            else {
                visitedColFence[i][j] = true;
                //왼쪽 위 가로
                if (j != 0 && tmpRow[i][j-1] && !visitedRowFence[i][j-1]){
                    stack.push(new int[]{i,j,1});
                    j--;
                    isRow = true;
                }
                //왼쪽 아래 가로
                else if (i != FIELD_ROW && j != 0 && tmpRow[i+1][j-1] && !visitedRowFence[i+1][j-1]){
                    stack.push(new int[]{i,j,1});
                    i++;
                    j--;
                    isRow = true;
                }
                //오른쪽 위 가로
                else if (j != FIELD_COL && tmpRow[i][j] && !visitedRowFence[i][j]){
                    stack.push(new int[]{i,j,1});
                    isRow = true;
                }
                //오른쪽 아래 가로
                else if (i != FIELD_ROW && j != FIELD_COL && tmpRow[i+1][j] && !visitedRowFence[i+1][j]){
                    stack.push(new int[]{i,j,1});
                    i++;
                    isRow = true;
                }
                //위 세로
                else if (i != 0 && tmpCol[i-1][j] && !visitedColFence[i-1][j]){
                    stack.push(new int[]{i,j,1});
                    i--;
                }
                //아래 세로
                else if (i != FIELD_ROW-1 && tmpCol[i+1][j] && !visitedColFence[i+1][j]){
                    stack.push(new int[]{i,j,1});
                    i++;
                }
                else if (stack.empty()) break;
                else {
                    int[] pos = stack.pop();
                    i = pos[0];
                    j = pos[1];
                    if (pos[2] == 0) isRow = true;
                }
            }
        }
        if (!Arrays.deepEquals(tmpRow,visitedRowFence) || !Arrays.deepEquals(tmpCol,visitedColFence)) throw new IllegalRequestException("울타리가 이어지지 않았습니다.");

        // 울타리 모양이 적합한지 확인
        boolean[][] check = new boolean[fields.length][fields[0].length];//false
        boolean[][] checkCol = new boolean[tmpCol.length][tmpCol[0].length];
        Stack<int[]> explorer = new Stack<>();
        Stack<int[]> buildPos = new Stack<>();
        int fieldRow = fields.length;
        int fieldCol = fields[0].length;
        int buildNum = 0;
        int[] pos = new int[2];
        for (i=0;i<tmpRow.length-1;i++){
            for (j=0;j<tmpRow[0].length;j++){
                if(tmpRow[i][j] && !check[i][j] && (fields[i][j] == null || fields[i][j].getFieldType() == FieldType.BARN || fields[i][j].getFieldType() == FieldType.STABLE)){
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
                        else if (explorer.empty()) throw new IllegalRequestException("적합하지 않은 위치입니다.");
                        else if ((pos[0]==0 && !tmpRow[pos[0]][pos[1]]) || (pos[1]==0 && !tmpCol[pos[0]][pos[1]]) ||
                                (pos[0]==check.length-1 && !tmpRow[pos[0]+1][pos[1]]) || (pos[1]==check[0].length-1 && !tmpCol[pos[0]][pos[1]+1])) {
                            explorer.removeAllElements();
                            buildPos.removeAllElements();
                            break;
                        }
                        else {
                            buildPos.push(pos.clone());
                            pos = explorer.pop();
                        }
                    }
                    while (!buildPos.empty()){
                        pos = buildPos.pop();
                        buildNum++;
                        checkCol[pos[0]][pos[1]] = tmpCol[pos[0]][pos[1]];
                        checkCol[pos[0]][pos[1]+1] = tmpCol[pos[0]][pos[1]+1];
                    }
                    check[i][j] = true;
                }
            }
        }
        if (!Arrays.deepEquals(checkCol,tmpCol)) throw new IllegalRequestException("적합하지 않은 위치입니다.");
        if (buildNum == 0) throw new IllegalRequestException("적합하지 않은 울타리입니다.");
        // 울타리 설치 적용
        rowFence = tmpRow.clone();
        colFence = tmpCol.clone();

        buildPos = new Stack<>();
        explorer = new Stack<>();
        check = new boolean[fields.length][fields[0].length];
        int stableNum = 0;

        for (i=0;i<tmpRow.length-1;i++){
            for (j=0;j<tmpRow[0].length;j++){
                if(tmpRow[i][j] && !check[i][j] && (fields[i][j] == null || fields[i][j].getFieldType() == FieldType.BARN || fields[i][j].getFieldType() == FieldType.STABLE)){
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
                            buildPos.removeAllElements();
                            break;
                        }
                        else if (pos[0] == i && pos[1] == j){
                            buildPos.push(pos.clone());
                            break;
                        }
                        else if (explorer.empty()) throw new IllegalRequestException("적합하지 않은 위치입니다.");
                        else {
                            if (fields[pos[0]][pos[1]] != null && ((Barn)(fields[pos[0]][pos[1]])).isStable()) stableNum++;
                            buildPos.push(pos.clone());
                            pos = explorer.pop();
                        }
                    }
                    // 헛간 지정
                    boolean isEmpty = buildPos.empty();
                    while (!buildPos.empty()){
                        pos = buildPos.pop();
                        if (fields[pos[0]][pos[1]] == null) {
                            fields[pos[0]][pos[1]] = Barn.builder()
                                    .fieldType(FieldType.BARN)
                                    .build();
                        }
                        ((Barn)fields[pos[0]][pos[1]]).setCapacity(stableNum);
                    }
                    if (!isEmpty) setSameAnimalType(pos[0],pos[1]);
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
                .filter(Objects::nonNull)
                .filter(field -> field.getFieldType() == FieldType.ROOM && ((Room) field).isEmptyRoom())
                .map(field -> (Room) field)
                .findFirst();

        if (emptyRoom.isPresent()) {
            emptyRoom.get().addResident(ResidentType.CHILD);
            return;
        }

        // 없으면 아무 방에 추가
        Arrays.stream(fields)
                .flatMap(Arrays::stream)
                .filter(Objects::nonNull)
                .filter(field -> field.getFieldType() == FieldType.ROOM)
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
                .filter(Objects::nonNull)
                .filter(field -> field.getFieldType() == FieldType.ROOM)
                .mapToInt(field -> ((Room) field).getResidentNumber())
                .sum();
    }

    /**
     * 선택한 동물이 있는 위치와 마리 수 반환
     * @param animalType 동물 타입
     * @return 내용물이 [row, col, count]인 list
     */
    protected List<int[]> animalPos(AnimalType animalType){
        Barn barn;
        List<int[]> pos = new ArrayList<>();
        // 애완동물 집 확인
        if (((Room)fields[1][0]).getPet() == animalType) pos.add(new int[]{1,0,1});

        for (int i=0;i<fields.length;i++){
            for (int j=0;j< fields[0].length;j++){
                if (fields[i][j] != null && (fields[i][j].getFieldType() == FieldType.BARN || fields[i][j].getFieldType() == FieldType.STABLE)){
                    barn = (Barn)fields[i][j];
                    if(barn.getAnimal().getAnimal() == animalType) pos.add(new int[]{i,j, barn.getAnimal().getCount()});
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
        if (fields[row][col] == null || fields[row][col].getFieldType() != FieldType.BARN && fields[row][col].getFieldType() != FieldType.STABLE) throw new IllegalRequestException("해당 필드는 헛간이 아닙니다.");
        AnimalType animalType = ((Barn)fields[row][col]).getAnimal().getAnimal();
        int num = ((Barn)fields[row][col]).removeAnimal(animalNum);
        moveAnimalArr[animalType.getValue()-9].addResource(num);
        ifEmptyBarn(row,col);
        return num;
    }

    /**
     * 해당 칸의 모든 동물을 제거합니다.
     * @param row row
     * @param col col
     * @return 제거된 동물 수
     */
    protected int removeALLAnimals(int row, int col){
        if (fields[row][col] == null || fields[row][col].getFieldType() != FieldType.BARN && fields[row][col].getFieldType() != FieldType.STABLE) throw new IllegalRequestException("해당 필드는 헛간이 아닙니다.");
        AnimalType animalType = ((Barn)fields[row][col]).getAnimal().getAnimal();
        int num = ((Barn)fields[row][col]).removeAllAnimals();
        moveAnimalArr[animalType.getValue()-9].addResource(num);
        ifEmptyBarn(row,col);
        return num;
    }

    private void ifEmptyBarn(int row, int col){
        Barn barn = (Barn)fields[row][col];
        if (barn.getAnimal() != null) return;
        setSameAnimalType(row, col);
    }

    private void setSameAnimalType(int row, int col) {
        Barn barn;
        List<int[]> list = sameBarnList(row, col);
        AnimalType exist = null;
        for (int[] pos : list){
            barn = (Barn)fields[pos[0]][pos[1]];
            if (barn.getAnimal() != null) {
                exist = barn.getAnimal().getAnimal();
                break;
            }
        }
        for (int[] pos : list){
            barn = (Barn)fields[pos[0]][pos[1]];
            if (exist == null) barn.getAnimal().setAnimal(null);
            else barn.getAnimal().setAnimal(exist);
        }
    }

    private List<int[]> sameBarnList(int row, int col) {
        Stack<int[]> explorer = new Stack<>();
        List<int[]> list = new ArrayList<>();
        list.add(new int[]{row, col});
        boolean[][] check = new boolean[FIELD_ROW][FIELD_COL];
        int i = row;
        int j = col;
        while(true) {
            check[row][col] = true;
            // 오른쪽
            if (col != FIELD_COL-1 && !colFence[row][col +1] && !check[row][col +1]){
                explorer.push(new int[]{row, col});
                col += 1;
            }
            // 아래
            else if (row != FIELD_ROW-1 && !rowFence[row +1][col] && !check[row +1][col]){
                explorer.push(new int[]{row, col});
                row += 1;
            }
            // 왼쪽
            else if (col != 0 && !colFence[row][col] && !check[row][col -1]){
                explorer.push(new int[]{row, col});
                col -= 1;
            }
            // 위
            else if (row != 0 && !rowFence[row][col] && !check[row -1][col]){
                explorer.push(new int[]{row, col});
                row -= 1;
            }
            // 정상적으로 설치했으면 호출될일 없음
            else if ((row ==0 && !rowFence[row][col]) || (col ==0 && !colFence[row][col]) ||
                    (row ==FIELD_ROW-1 && !rowFence[row +1][col]) || (col ==FIELD_COL-1 && !colFence[row][col +1])) {
                explorer.removeAllElements();
                throw new RuntimeException("울타리 구조가 비정상적입니다. 이거 호출되면 안되는데");
            }
            else if (row == i && col == j){
                list.add(new int[]{row, col});
                break;
            }
            else if (explorer.empty()) return null;
            else {
                list.add(new int[]{row,col});
                int[] pos = explorer.pop();
                row = pos[0];
                col = pos[1];
            }
        }
        return list;
    }

    /**
     * 이동 스택에 쌓아둔 동물들을 특정 위치로 재배치하는 함수
     * @param row row
     * @param col col
     * @param animalType 추가할 동물의 타입
     * @param animalNum 추가할 동물 수
     * @return 이동한 동물 수(수용가능한 동물 양에 따라 결과값이 달라짐)
     */
    protected int addRemovedAnimal(int row, int col, AnimalType animalType, int animalNum){
        if (fields[row][col] == null || fields[row][col].getFieldType() != FieldType.BARN && fields[row][col].getFieldType() != FieldType.STABLE) throw new IllegalRequestException("해당 필드는 헛간이 아닙니다.");
        animalNum = Integer.min(animalNum,moveAnimalArr[animalType.getValue()-9].getCount());
        int num = ((Barn)fields[row][col]).addAnimal(animalType, animalNum);
        moveAnimalArr[animalType.getValue()-9].subResource(num);
        setSameAnimalType(row,col);
        return num;
    }

    /**
     * TODO 초과된 동물을 removed animal 로 간주하고 추가 입력
     * 행동칸이나 수확행동을 통해 동물을 추가 하였을때 자동으로 배치하는 함수
     * @param animalType 동물 타입
     * @param animalNum 동물 수
     * @return 행동 성공 여부
     */
    protected boolean addAnimal(AnimalType animalType, int animalNum){
        Barn barn;
        for (int i = 0; i < fields[0].length; i++) {
            for (int j = 0; j < fields[0].length; j++) {
                if (fields[i][j] != null && (fields[i][j].getFieldType() == FieldType.BARN || fields[i][j].getFieldType() == FieldType.STABLE)) {
                    barn = (Barn) fields[i][j];
                    animalNum -= barn.addAnimal(animalType, animalNum);
                }
                setSameAnimalType(i,j);
                if (animalNum == 0) return true;
            }
        }
        if (animalNum == 1 && (((Room)fields[1][0]).getPet() == animalType || ((Room)fields[1][0]).getPet() == null)){
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
        AnimalType animalType;
        int num;
        for (int i = 0; i < fields[0].length; i++) {
            for (int j = 0; j < fields[0].length; j++) {
                if (fields[i][j] != null && (fields[i][j].getFieldType() == FieldType.BARN || fields[i][j].getFieldType() == FieldType.STABLE)) {
                    barn = (Barn) fields[i][j];
                    animalType = barn.getAnimal().getAnimal();
                    if (animalType != null) {
                        num = barn.removeAllAnimals();
                        moveAnimalArr[animalType.getValue()].addResource(num);
                        setSameAnimalType(i,j);
                    }
                }
            }
        }
        //애완동물
        if(((Room)fields[1][0]).getPet() != null) {
            animalType = ((Room)fields[1][0]).removePet();
            moveAnimalArr[animalType.getValue()].addResource(1);
        }
    }

    /**
     * 필드에서 임시적으로 제거해둔 동물 배열을 초기화 (동물 삭제)
     */
    protected void initMoveAnimalArr(){
        for (AnimalStruct animalStruct : moveAnimalArr) {
            animalStruct.setCount(0);
        }
    }

    /**
     * 필드 개수
     * @param fieldType
     * @return
     */
    public int getNumField(FieldType fieldType){
        return (int)Arrays.stream(fields)
                .flatMap(Arrays::stream)
                .filter(field -> field != null && field.getFieldType().equals(fieldType))
                .count();
    }

    /**
     * 헛간안에 외양간이 있는 수(포인트 계산용)
     * @return
     */
    public int numStableInBarn(){
        return (int) Arrays.stream(fields)
                .flatMap(Arrays::stream)
                .filter(field -> field != null && field.getFieldType().equals(FieldType.BARN) && ((Barn)field).isStable())
                .count();
    }

    /**
     * 보드의 빈칸 개수(포인트 계산용)
     * @return
     */
    public int numEmptyField(){
        return (int) Arrays.stream(fields)
                .flatMap(Arrays::stream)
                .filter(Objects::isNull)
                .count();
    }

    /**
     * test 함수
     */
    public void printField() {
        for (int i = 0; i < fields.length; i++) {
            for (int j = 0; j < fields[0].length; j++) {
                if (fields[i][j] == null) System.out.print("null");
                else if (fields[i][j] != null) System.out.print(fields[i][j].getFieldType().toString());
                else System.out.print("error");
                System.out.print(" ");
            }
            System.out.println();
        }
    }
    /**
     * 경작 가능성 검증
     */
    private void acceptFarm(int y, int x) {
        if (y < 0 || y >= FIELD_ROW || x < 0 || x >= FIELD_COL) {
            throw new NotAllowRequestException("필드 위치가 부적절합니다.");
        }

        if (this.fields[y][x].getFieldType() != FieldType.FARM ||
                (((Farm) this.fields[y][x]).getSeed().getResource() != null &&
                        ((Farm) this.fields[y][x]).getSeed().getCount() != 0))
            throw new IllegalRequestException("빈 밭이 아닙니다.");
    }

    /**
     * 경작
     * @param y
     * @param x
     * @param resourceType
     */
    public void cultivate(int y, int x, ResourceType resourceType) {
        acceptFarm(y, x);
        ((Farm) this.fields[y][x]).cultivate(resourceType);
    }

    /**
     * 빈 밭인지 확인
     * @param y 플레이어 필드 column 위치
     * @param x 플레이어 필드 row 위치
     * @return 빈 밭일 경우 true를 반환한다,
     */
    public boolean isEmptyFarm(int y, int x) {
        return this.fields[y][x].getFieldType() == FieldType.FARM &&
                ((Farm) this.fields[y][x]).getSeed().getResource() == null ||
                ((Farm) this.fields[y][x]).getSeed().getCount() == 0;
    }

    public void playAction() {
        Arrays.stream(fields)
                .flatMap(Arrays::stream)
                .filter(field -> field instanceof Room)
                .map(field -> (Room) field)
                .filter(room -> !room.isCompletedPlayed())
                .findFirst()
                .orElseThrow(ServerError::new)
                .play();
    }

    public int getAnimal(AnimalType animalType) {
        int num;
        num = (int)Arrays.stream(fields)
                .flatMap(Arrays::stream)
                .filter(field -> field != null &&
                        (field.getFieldType() == FieldType.BARN || field.getFieldType() == FieldType.STABLE) &&
                        ((Barn)field).getAnimal().getAnimal() == animalType)
                .mapToLong(field -> ((Barn)field).getAnimal().getCount())
                .sum();

        //애완동물
        if(((Room)fields[1][0]).getPet() != null && ((Room)fields[1][0]).getPet() == animalType) {
            num += 1;
        }
        return num;
    }
}
