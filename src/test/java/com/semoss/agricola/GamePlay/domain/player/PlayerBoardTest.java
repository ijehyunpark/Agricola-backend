package com.semoss.agricola.GamePlay.domain.player;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayerBoardTest {
    PlayerBoard playerBoard1;
    PlayerBoard playerBoard2;
    PlayerBoard playerBoard3;

    boolean[][] row1 = {{true,true,true,true,true},{true,true,true,true,true},{false,true,true,true,true},{false,false,true,true,true}};
    boolean[][] col1 = {{true,true,true,true,true,true},{false,true,true,true,true,true},{false,false,true,true,true,true}};

    boolean[][] row2 = {{true,true,true,false,false},{true,true,true,true,false},{false,true,true,true,true},{false,true,true,true,true}};
    boolean[][] col2 = {{true,true,true,true,false,false},{false,true,true,true,true,false},{false,true,true,true,true,true}};

    boolean[][] row3 = {{true,true,true,true,true},{true,true,true,true,true},{false,true,true,true,true},{false,true,true,true,true}};
    boolean[][] col3 = {{true,true,true,true,true,true},{false,true,true,true,true,true},{false,true,true,true,true,true}};

    int[][][] fence1 = {{{0,3},{1,2},{1,4},{2,2},{2,4},{3,3}},{{0,3},{0,4},{1,2},{1,5},{2,3},{2,4}}};
    int[][][] fence2 = {{{1,1},{1,3},{2,2},{3,1},{3,2},{3,3}},{{1,1},{1,2},{1,3},{1,4},{2,1},{2,4}}};
    int[][][] fence3_1 = {{{2,2},{2,3},{2,4},{3,2},{3,3},{3,4}},{{2,2},{2,5}}};
    int[][][] fence3_2 = {{{1,4}},{{1,4},{1,5}}};
    int[][][] fence3_3 = {{{0,3},{0,4},{1,3}},{{0,3},{0,5}}};
    int[][][] fence_error = {{{0,1}},{{0,1},{0,2},{1,1},{1,2}}};

    boolean[][][] tmpPos;

    @BeforeEach
    void setUp() {
        playerBoard1 = PlayerBoard.builder().build();
        playerBoard1.buildField(2,1,FieldType.ROOM);

        playerBoard2 = PlayerBoard.builder().build();
        playerBoard2.buildField(0,3,FieldType.FARM);
        playerBoard2.buildField(0,4,FieldType.FARM);
        playerBoard2.buildField(1,4,FieldType.FARM);

        playerBoard3 = PlayerBoard.builder().build();
        playerBoard3.buildField(1,2,FieldType.FARM);
    }

    @Test
    void availableFencePos() {
        tmpPos = playerBoard1.availableFencePos();
        int i;
        for (i=0;i<row1.length;i++){
            assertArrayEquals(tmpPos[0],row1);
        }
        for (i=0;i<col1.length;i++){
            assertArrayEquals(tmpPos[1],col1);
        }

        tmpPos = playerBoard2.availableFencePos();
        for (i=0;i<row2.length;i++){
            assertArrayEquals(tmpPos[0],row2);
        }
        for (i=0;i<col2.length;i++){
            assertArrayEquals(tmpPos[1],col2);
        }

        tmpPos = playerBoard3.availableFencePos();
        for (i=0;i<row3.length;i++){
            assertArrayEquals(tmpPos[0],row3);
        }
        for (i=0;i<col3.length;i++){
            assertArrayEquals(tmpPos[1],col3);
        }
    }

    @Test
    void buildFence() {
        assertTrue(playerBoard1.buildFence(fence1[0],fence1[1]));
        assertTrue(playerBoard2.buildFence(fence2[0],fence2[1]));
        assertTrue(playerBoard3.buildFence(fence3_1[0],fence3_1[1]));
        assertTrue(playerBoard3.buildFence(fence3_2[0],fence3_2[1]));
        assertTrue(playerBoard3.buildFence(fence3_3[0],fence3_3[1]));

        assertFalse(playerBoard1.buildFence(fence_error[0],fence_error[1]));
        assertFalse(playerBoard2.buildFence(fence_error[0],fence_error[1]));
        assertFalse(playerBoard3.buildFence(fence_error[0],fence_error[1]));

    }
}