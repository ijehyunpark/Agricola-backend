package com.semoss.agricola.GamePlay.domain.Action;

import com.semoss.agricola.GamePlay.domain.Card.Card;
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

    /**
     * Are there enough resources for the action. Doesn't check additional resources required place cards
     * @param player player who check precondition
     * @return result
     */
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

    /**
     * player place own card(occupation,minor) or get major card
     * @param player player who place card
     * @param cardID card ID
     * @return result
     */
    public boolean runAction(Player player, String cardID){
        Card card = CardDictionary.cardDict.get((cardID));
        if(card.getCardType() != cardType) return false;
        if(!card.checkPrerequisites(player)) return false;
        card.useResource(player);
        if(cardType == CardType.MAJOR) return player.getMajorCard(cardID);
        return player.placeCard(cardID);
    }

    @Override
    public ActionType getActionType() {
        return actionType;
    }
}
