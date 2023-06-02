package com.semoss.agricola.GamePlay.domain.resource;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum ResourceType {
    WOOD(1, "나무"), CLAY(2, "흙"), STONE(3, "돌"), REED(4, "갈대"), GRAIN(5, "곡식"),
    VEGETABLE(6, "야채"), FOOD(7, "음식 토큰"), BEGGING(8, "구걸 토큰");

    private final int value;
    private final String name;

    ResourceType(int value, String name) {
        this.value = value;
        this.name = name;
    }

    @JsonCreator
    public static ResourceType fromValue(String name) {
        for (ResourceType resourceType : ResourceType.values()) {
            if (resourceType.name().equals(name)) {
                return resourceType;
            }
        }

        for (ResourceType resourceType : ResourceType.values()) {
            if (resourceType.name.equals(name)) {
                return resourceType;
            }
        }

        throw new IllegalArgumentException("Invalid Status value: " + name);
    }


    public int getValue() { return value; }

    @JsonValue
    public String getName() { return name(); }
}
