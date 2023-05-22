package com.semoss.agricola.GamePlay.domain.player;

/**
 * 방의 종류
 */
public enum RoomType {
    WOOD(0),
    CLAY(1),
    STONE(2);

    private final int value;

    RoomType(int value) { this.value = value; }

    public int getValue() { return value; }
}
