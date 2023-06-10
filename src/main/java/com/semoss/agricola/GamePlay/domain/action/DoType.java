package com.semoss.agricola.GamePlay.domain.action;

public enum DoType {
    ANDOR(1), OR(2), AFTER(3), FINISH(4);
    private final int value;

    DoType(int value) { this.value = value; }

    public int getValue() { return value; }
}
