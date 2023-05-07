package com.semoss.agricola.mainflow;

import com.semoss.agricola.GamePlay.domain.Field.Field;
import com.semoss.agricola.GamePlay.domain.Field.FieldType;

import java.util.HashMap;

public class Player {
    private String userID;
    private HashMap<ResourceType,Integer> resourceStorage;
    private PlayerBoard playerBoard;

    public Player(String userID){
        this.userID = userID;
        resourceStorage = new HashMap<>();
        for (ResourceType rt : ResourceType.values()){
            resourceStorage.put(rt,0);
        }
        playerBoard = new PlayerBoard(userID);
    }

    /**
     * increase family action's precondition
     * @return if player has empty room, return true
     */
    public boolean familyPrecondition(){
        return playerBoard.getRoomCount() > resourceStorage.get(ResourceType.FAMILY);
    }

    /**
     * add resources to storage
     * @param resourceType resource type to add
     * @param num amount of resource
     */
    public void addResource(ResourceType resourceType, int num){
        resourceStorage.put(resourceType,resourceStorage.get(resourceType)+num);
    }

    /**
     * use resource
     * @param resourceType resource type to use
     * @param num amount of resource
     * @return Whether to use
     */
    public boolean useResource(ResourceType resourceType, int num){
        if (resourceStorage.get(resourceType) < num) return false;
        resourceStorage.put(resourceType,resourceStorage.get(resourceType)-num);
        return true;
    }

    /**
     * get player's resource
     * @param resourceType resource type to get
     * @return amount of resource
     */
    public int getResource(ResourceType resourceType){
        return resourceStorage.get(resourceType);
    }

    public FieldType getRoomType(){
        return playerBoard.getRoomType();
    }

    public int getRoomCount(){
        return playerBoard.getRoomCount();
    }

    public boolean buildField(int[][] pos, FieldType fieldType){
        switch (fieldType){
            case FARM -> {
                return playerBoard.buildFarm(pos);
            }
            case STABLE -> {
                return playerBoard.buildStable(pos);
            }
            case FENCE -> {
                return playerBoard.buildFence(pos);
            }
        }
        return false;
    }

    public void upgradeRoom() {
        playerBoard.upgradeRoom();
    }

    /**
     * for test. don't use
     * @param num
     */
    public void setRoomCount(int num) {
        playerBoard.setRoomCount(num);
    }

    /**
     * for test
     */
    public Field getField(int row, int col){
        return playerBoard.getField(row,col);
    }
}
