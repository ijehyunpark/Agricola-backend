package com.semoss.agricola.GamePlay.domain.resource;

import com.fasterxml.jackson.annotation.JsonValue;

public enum ResourceType {
    EMPTY(0), WOOD(1), CLAY(2), STONE(3), REED(4), GRAIN(5), VEGETABLE(6),
    FOOD(7), BEGGING(8), CARD(9), FAMILY(10);

    private final int value;

    ResourceType(int value) { this.value = value; }

    public int getValue() { return value; }

    @JsonValue
    public String getName() { return name(); }
}
