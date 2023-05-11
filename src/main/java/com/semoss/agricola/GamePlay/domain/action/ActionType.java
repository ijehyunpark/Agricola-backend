package com.semoss.agricola.GamePlay.domain.action;


public enum ActionType {
    EMPTY(0), STACK(1), CULTIVATION(2), BUILD(3), UPGRADE(4), BAKE(5), STARTING(6),
    PLACE(7), BASIC(8);
    private final int value;

    ActionType(int value) { this.value = value; }

    public int getValue() { return value; }
}
