package com.semoss.agricola.GamePlay.domain.resource;

public enum ResourceType {
    EMPTY(0), WOOD(1), CLAY(2), STONE(3), REED(4), GRAIN(5), VEGETABLE(6),
    FOOD(7), BEGGING(8), SHEEP(9), WILD_BOAR(10), CATTLE(11), CARD(12);

    private final int value;

    ResourceType(int value) { this.value = value; }

    public int getValue() { return value; }
}
