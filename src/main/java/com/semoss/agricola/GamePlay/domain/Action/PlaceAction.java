package com.semoss.agricola.GamePlay.domain.Action;

import com.semoss.agricola.GamePlay.domain.Card.CardDictionary;
import com.semoss.agricola.GamePlay.domain.Card.CardType;
import com.semoss.agricola.GamePlay.domain.Player;
import com.semoss.agricola.GamePlay.domain.ResourceType;

public class PlaceAction implements Action{
    private final ActionType actionType;
    private final ResourceType resourceType;
    private final int resourceNum;
    private final CardType cardType;

    public PlaceAction(ResourceType resourceType,int resourceNum, CardType cardType){
        this.resourceType = resourceType;
        this.resourceNum = resourceNum;
        this.cardType = cardType;
        actionType = ActionType.PLACE;
    }

    @Override
    public boolean checkPrecondition(Player player) {
        return player.getResource(resourceType) >= resourceNum && player.hasCardInHand(cardType);
    }

    @Override
    public boolean runAction() {
        return true;
    }

    @Override
    public boolean runAction(Player player) {
        return true;
    }

    public boolean runAction(Player player, String cardID){
        if(CardDictionary.cardDict.get(cardID).getCardType() != cardType) return false;
        if(cardType == CardType.MAJOR) return player.getMajorCard(cardID);
        return player.placeCard(cardID);
    }

    @Override
    public ActionType getActionType() {
        return actionType;
    }
}
