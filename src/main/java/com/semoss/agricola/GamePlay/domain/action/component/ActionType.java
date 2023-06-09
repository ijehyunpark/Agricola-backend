package com.semoss.agricola.GamePlay.domain.action.component;


public enum ActionType {
    STACK(1), CULTIVATION(2), BUILD(3), UPGRADE(4), BAKE(5), STARTING(6),
    PLACE(7), BASIC(8), ADOPT(9), STACK_ANIMAL(10), BUILD_ROOM(11), BUILD_FENCE(12);
    private final int value;

    ActionType(int value) { this.value = value; }

    public int getValue() { return value; }
}
