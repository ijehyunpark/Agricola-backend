package com.semoss.agricola.GamePlay.domain.Card;

public class MinorCard implements Card{
    private CardType cardType;
    private String cardID;

    public MinorCard(String id){
        cardType = CardType.OCCUPATION;
        cardID = id;
    }
    @Override
    public CardType getCardType() {
        return null;
    }
}
