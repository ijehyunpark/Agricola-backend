package com.semoss.agricola.GamePlay.domain.field;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Barn implements Field {
    private AnimalType animalType;
    private int animalNum;
    private boolean stable;
    private int capacity;
}
