package com.semoss.agricola.GamePlay.domain;

import com.semoss.agricola.GamePlay.domain.Field.Field;
import com.semoss.agricola.GamePlay.domain.Field.FieldType;
import com.semoss.agricola.GamePlay.domain.Field.Room;

public class PlayerBoard {
    private String userID;
    private int roomCount;
    private FieldType roomType;
    private int farmCount;
    private int fenceCount;
    private int stableCount;
    private FieldType[][] fieldStatus = new FieldType[3][5];
    private Field[][] fields = new Field[3][5];

    public PlayerBoard(String userID){
        this.userID = userID;
        roomType = FieldType.WOOD;
        roomCount = 2;
        fieldStatus[2][0] = FieldType.WOOD;
        fieldStatus[1][0] = FieldType.WOOD;
        fields[2][0] = new Room(FieldType.WOOD);
        fields[1][0] = new Room(FieldType.WOOD);
    }

    public int getRoomCount() {
        return roomCount;
    }

    public FieldType getRoomType() {
        return roomType;
    }

    /**
     * for test. don't use.
     * @param roomCount
     */
    public void setRoomCount(int roomCount) {
        this.roomCount = roomCount;
    }

    public boolean buildFarm(int[][] pos) {
        return true;
    }

    public boolean buildStable(int[][] pos) {
        return true;
    }

    public boolean buildFence(int[][] pos) {
        return true;
    }

    public void upgradeRoom() {
        if (roomType == FieldType.WOOD) roomType = FieldType.CLAY;
        else if (roomType == FieldType.CLAY) roomType = FieldType.STONE;
        else return;
        for (int i=0;i<fieldStatus.length;i++){
            for (int j=0;j<fieldStatus[0].length;j++){
                if(fieldStatus[i][j] == FieldType.WOOD || fieldStatus[i][j] == FieldType.CLAY)
                    ((Room) fields[i][j]).upgradeRoom();
            }
        }
    }

    /**
     * for test
     * @return
     */
    public Field getField(int row, int col){
        return fields[row][col];
    }
}
