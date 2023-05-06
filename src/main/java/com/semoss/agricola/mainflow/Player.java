package com.semoss.agricola.mainflow;

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

    public int getResource(ResourceType resourceType){
        return resourceStorage.get(resourceType);
    }

    /**
     * for test. don't use
     * @param num
     */
    public void setRoomCount(int num) {
        playerBoard.setRoomCount(num);
    }
}
