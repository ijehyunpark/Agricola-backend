package com.semoss.agricola.GamePlay.domain.field;

import lombok.Getter;

@Getter
public class Farm implements Field {
    private int cropType;
    private int cropNum;
}
