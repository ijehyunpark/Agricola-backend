package com.semoss.agricola.GamePlay.domain.Card;

import com.semoss.agricola.GamePlay.domain.Player;

public class MinorCard implements Card{
    private CardType cardType;
    private String cardID;
    private String owner;

    public MinorCard(String id){
        cardType = CardType.OCCUPATION;
        cardID = id;
        owner = "";
    }

    @Override
    public boolean checkPrerequisites(Player player) {
        return true;
    }

    @Override
    public CardType getCardType() {
        return cardType;
    }

    @Override
    public String getOwner() {
        return owner;
    }

    @Override
    public void setOwner(String userID) {
        owner = userID;
    }

    @Override
    public void useResource(Player player) {

    }
}
