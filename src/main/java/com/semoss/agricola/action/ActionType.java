package com.semoss.agricola.action;

public enum ActionType {
    EMPTY(0), STACK(1), CULTIVATION(2), BUILD(3), BAKE(4), STARTING(5),
    PLACE(6), BASIC(7);
    private final int value;

    ActionType(int value) { this.value = value; }

    public int getValue() { return value; }
}
