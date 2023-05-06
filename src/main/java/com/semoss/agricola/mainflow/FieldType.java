package com.semoss.agricola.mainflow;

public enum FieldType {
    EMPTY(0), WOOD(1), CLAY(2), STONE(3), FARM(4), BARN(5),
    STABLE(6), FENCE(7);

    private final int value;

    FieldType(int value) { this.value = value; }

    public int getValue() { return value; }
}