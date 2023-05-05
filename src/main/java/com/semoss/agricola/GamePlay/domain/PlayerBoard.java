package com.semoss.agricola.GamePlay.domain;

import com.semoss.agricola.GamePlay.domain.Field.Field;

import java.util.List;

public class PlayerBoard {
    private int roomCount;
    private int roomType;
    private int farmCount;
    private int fenceCount;
    private int stableCount;
    private int[][] fieldStatus = new int[3][5];
    private Field[][] fields = new Field[3][5];
    private int[][] fence = new int[4][6];
    private int petType;

}
