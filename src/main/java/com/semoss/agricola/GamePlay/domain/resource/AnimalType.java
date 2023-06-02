package com.semoss.agricola.GamePlay.domain.resource;

public enum AnimalType {
    WILD_BOAR(0),
    SHEEP(1),
    CATTLE(2);

    private final int value;

    AnimalType(int value) { this.value = value; }

    public int getValue() { return value; }

    public String getName() {return name(); }
}
