package com.semoss.agricola.GamePlay.domain.Card;

public enum CardType {
    EMPTY(0), OCCUPATION(1), MINOR(2), MAJOR(3);
    private final int value;

    CardType(int value) { this.value = value; }

    public int getValue() { return value; }
}
