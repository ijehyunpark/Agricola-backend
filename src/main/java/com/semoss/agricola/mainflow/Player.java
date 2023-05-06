package com.semoss.agricola.mainflow;

import java.util.HashMap;

public class Player {
    private String userID;
    private HashMap<ResourceType,Integer> resourceStorage;

    public Player(String userID){
        this.userID = userID;
        this.resourceStorage = new HashMap<>();
        for (ResourceType rt : ResourceType.values()){
            this.resourceStorage.put(rt,0);
        }
    }

    public void addResource(ResourceType resourceType, int num){
        resourceStorage.put(resourceType,resourceStorage.get(resourceType)+num);
    }

    public int getResource(ResourceType resourceType){
        return resourceStorage.get(resourceType);
    }
}
