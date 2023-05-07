package com.semoss.agricola.mainflow;

public class PlayerBoard {
    private String userID;
    private int roomCount;
    private FieldType roomType;
    private int farmCount;
    private int fenceCount;
    private int stableCount;

    public PlayerBoard(String userID){
        this.userID = userID;
        roomType = FieldType.WOOD;
        roomCount = 2;
    }

    public int getRoomCount() {
        return roomCount;
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
}
