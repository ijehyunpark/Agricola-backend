package com.semoss.agricola.GamePlay.domain;

import com.semoss.agricola.GamePlay.domain.Card.Card;
import com.semoss.agricola.GamePlay.domain.Card.CardDictionary;
import com.semoss.agricola.GamePlay.domain.Card.CardType;
import com.semoss.agricola.GamePlay.domain.Field.Field;
import com.semoss.agricola.GamePlay.domain.Field.FieldType;

import java.util.*;

public class Player {
    private String userID;
    private final EnumMap<ResourceType,Integer> resourceStorage;
    private final PlayerBoard playerBoard;
    private final List<String> cardHand;
    private final List<String> cardField;

    public Player(String userID){
        this.userID = userID;
        resourceStorage = new EnumMap<>(ResourceType.class);
        for (ResourceType rt : ResourceType.values()){
            resourceStorage.put(rt,0);
        }
        playerBoard = new PlayerBoard(userID);
        cardHand = new ArrayList<>();
        cardField = new ArrayList<>();
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

    /**
     * build field to player board
     * @param pos positions of fields
     * @param fieldType field type
     * @return result
     */
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
            default -> {
                return false;
            }
        }
    }

    /**
     * upgrade room
     */
    public void upgradeRoom() {
        playerBoard.upgradeRoom();
    }

    /**
     * does player have cards of card type in hand
     * @param cardType card type
     * @return result
     */
    public boolean hasCardInHand(CardType cardType) {
        for (String id : cardHand){
            if (CardDictionary.cardDict.get(id).getCardType() == cardType) return true;
        }
        return false;
    }

    /**
     * place card in hand to field. card becomes available.
     * @param cardID card ID
     * @return result
     */
    public boolean placeCard(String cardID){
        if(cardHand.remove(cardID)) {
            cardField.add(cardID);
            return true;
        }
        return false;
    }

    /**
     * get major improvement card.
     * @param cardID card ID
     * @return result
     */
    public boolean getMajorCard(String cardID){
        if (!Objects.equals(CardDictionary.cardDict.get(cardID).getOwner(),"")) return false;
        cardField.add(cardID);
        CardDictionary.cardDict.get(cardID).setOwner(userID);
        return true;
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
