package com.semoss.agricola.GamePlay.domain.player;

/**
 * 필드 타입
 */
public enum FieldType {
    ROOM(1), FARM(2), BARN(3), STABLE(4), FENCE(5);

    private final int value;

    FieldType(int value) { this.value = value; }

    public int getValue() { return value; }
}
