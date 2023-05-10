package com.semoss.agricola.GamePlay.domain.action;

public enum DoType {
    EMPTY(0), ANDOR(1), OR(2), NTIMES(3);
    private final int value;

    DoType(int value) { this.value = value; }

    public int getValue() { return value; }
}
