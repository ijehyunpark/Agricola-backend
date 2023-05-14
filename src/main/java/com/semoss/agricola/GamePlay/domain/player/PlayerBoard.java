package com.semoss.agricola.GamePlay.domain.player;

import com.semoss.agricola.GamePlay.domain.resource.ResourceStruct;
import jdk.jshell.spi.ExecutionControl;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

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
                            if (fieldStatus[pos[0]][pos[1]] != FieldType.EMPTY && ((Barn)(fields[pos[0]][pos[1]])).isStable()) stableNum++;
                            buildPos.push(pos.clone());
                            pos = explorer.pop();
                        }
                    }
                    // 헛간 지정
                    while (!buildPos.empty()){
                        pos = buildPos.pop();
                        if (fieldStatus[pos[0]][pos[1]] == FieldType.EMPTY) {
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
