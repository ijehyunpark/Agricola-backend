package com.semoss.agricola.GamePlay.domain.Card;

public class Occupation implements Card{
    private CardType cardType;
    private String cardID;

    public Occupation(String id){
        cardType = CardType.OCCUPATION;
        cardID = id;
    }
    @Override
    public CardType getCardType() {
        return null;
    }
}
