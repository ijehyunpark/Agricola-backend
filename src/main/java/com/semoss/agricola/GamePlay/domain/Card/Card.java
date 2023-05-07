package com.semoss.agricola.GamePlay.domain.Card;

public interface Card {
    CardType getCardType();
    String getOwner();
    void setOwner(String string);
}
