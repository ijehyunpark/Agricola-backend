package com.semoss.agricola.GamePlay.domain.action.component;

import com.semoss.agricola.GamePlay.domain.player.Barn;
import com.semoss.agricola.GamePlay.domain.player.FieldType;
import com.semoss.agricola.GamePlay.domain.player.Player;
import com.semoss.agricola.GamePlay.domain.resource.ResourceType;
import com.semoss.agricola.GamePlay.dto.BuildFenceActionExtensionRequest;
import com.semoss.agricola.GamePlay.exception.IllegalRequestException;
import com.semoss.agricola.GamePlay.exception.ResourceLackException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BuildFenceActionTest {
    Player player;
    BuildFenceAction action;

    @BeforeEach
    void setUp() {
        player = Player.builder().build();
        ActionFactory actionFactory = new ActionFactory();
        action = actionFactory.buildFenceAction();
    }

    /**
     * 경의로운 울타리 디버그 함수입니다, 놀라지 마세요
     */
    void printPlayerBoard() {
        player.getPlayerBoard().printField();
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 5; j++) {
                System.out.print(" ");
                if(player.getPlayerBoard().getRowFence()[i][j])
                    System.out.print("=");
                else
                    System.out.print("'");
            }
            System.out.println();
            for (int j = 0; i < 3 && j < 6; j++) {
                if(player.getPlayerBoard().getColFence()[i][j])
                    System.out.print("|");
                else
                    System.out.print("'");
                System.out.print(" ");
            }
            System.out.println();
        }
    }

    @Test
    @DisplayName("울타리 건설: 성공")
    void test1() {
        // given
        player.addResource(ResourceType.WOOD, 4);
        List<BuildFenceActionExtensionRequest> requests = List.of(
                new BuildFenceActionExtensionRequest(0,0, false),
                new BuildFenceActionExtensionRequest(1,0, false),
                new BuildFenceActionExtensionRequest(0,0, true),
                new BuildFenceActionExtensionRequest(0,1, true)
        );

        // when
        action.runAction(player, requests);

        // then
        printPlayerBoard();
        assertTrue(player.getPlayerBoard().getRowFence()[0][0]);
        assertTrue(player.getPlayerBoard().getRowFence()[1][0]);
        assertTrue(player.getPlayerBoard().getColFence()[0][0]);
        assertTrue(player.getPlayerBoard().getColFence()[0][1]);
        assertEquals(0, player.getResource(ResourceType.WOOD));
        assertEquals(FieldType.BARN, player.getPlayerBoard().getFields()[0][0].getFieldType());
        assertEquals(2, ((Barn) player.getPlayerBoard().getFields()[0][0]).getCapacity());
        assertNull(((Barn) player.getPlayerBoard().getFields()[0][0]).getAnimal().getAnimal());
    }

    @Test
    @DisplayName("울타리 건설 - 자원 부족: 실패")
    void test5() {
        // given
        List<BuildFenceActionExtensionRequest> requests = List.of(
                new BuildFenceActionExtensionRequest(0,0, false),
                new BuildFenceActionExtensionRequest(1,0, false),
                new BuildFenceActionExtensionRequest(0,0, true),
                new BuildFenceActionExtensionRequest(0,1, true)
        );

        // when
        assertThrows(
                ResourceLackException.class, () -> {
                    action.runAction(player, requests);
                    printPlayerBoard();
                }
        );

        // then
    }

    @Test
    @DisplayName("울타리 건설 - 집을 둘러싸기: 실패")
    void test2() {
        // given
        player.addResource(ResourceType.WOOD, 4);
        List<BuildFenceActionExtensionRequest> requests = List.of(
                new BuildFenceActionExtensionRequest(1,0, false),
                new BuildFenceActionExtensionRequest(2,0, false),
                new BuildFenceActionExtensionRequest(1,0, true),
                new BuildFenceActionExtensionRequest(1,1, true)
        );

        // when
        assertThrows(
                IllegalRequestException.class, () -> {
                    action.runAction(player, requests);
                    printPlayerBoard();
                }
        );

        // then
        printPlayerBoard();
    }

    @Test
    @DisplayName("울타리 건설 - 초 거대 울타리: 성공")
    void test3() {
        // given
        player.addResource(ResourceType.WOOD, 12);
        List<BuildFenceActionExtensionRequest> requests = List.of(
                new BuildFenceActionExtensionRequest(0,1, false),
                new BuildFenceActionExtensionRequest(0,2, false),
                new BuildFenceActionExtensionRequest(0,3, false),
                new BuildFenceActionExtensionRequest(3,1, false),
                new BuildFenceActionExtensionRequest(3,2, false),
                new BuildFenceActionExtensionRequest(3,3, false),
                new BuildFenceActionExtensionRequest(0,1, true),
                new BuildFenceActionExtensionRequest(1,1, true),
                new BuildFenceActionExtensionRequest(2,1, true),
                new BuildFenceActionExtensionRequest(0,4, true),
                new BuildFenceActionExtensionRequest(1,4, true),
                new BuildFenceActionExtensionRequest(2,4, true)
        );

        // when
        action.runAction(player, requests);

        // then
        printPlayerBoard();
        assertTrue(player.getPlayerBoard().getRowFence()[0][1]);
        assertTrue(player.getPlayerBoard().getRowFence()[0][2]);
        assertTrue(player.getPlayerBoard().getRowFence()[0][3]);
        assertTrue(player.getPlayerBoard().getRowFence()[3][1]);
        assertTrue(player.getPlayerBoard().getRowFence()[3][2]);
        assertTrue(player.getPlayerBoard().getRowFence()[3][3]);

        assertTrue(player.getPlayerBoard().getColFence()[0][1]);
        assertTrue(player.getPlayerBoard().getColFence()[1][1]);
        assertTrue(player.getPlayerBoard().getColFence()[2][1]);
        assertTrue(player.getPlayerBoard().getColFence()[0][4]);
        assertTrue(player.getPlayerBoard().getColFence()[1][4]);
        assertTrue(player.getPlayerBoard().getColFence()[2][4]);
        assertEquals(FieldType.BARN, player.getPlayerBoard().getFields()[0][1].getFieldType());
        assertEquals(FieldType.BARN, player.getPlayerBoard().getFields()[0][2].getFieldType());
        assertEquals(FieldType.BARN, player.getPlayerBoard().getFields()[1][1].getFieldType());
        assertEquals(FieldType.BARN, player.getPlayerBoard().getFields()[1][2].getFieldType());
        assertEquals(FieldType.BARN, player.getPlayerBoard().getFields()[2][1].getFieldType());
        assertEquals(FieldType.BARN, player.getPlayerBoard().getFields()[2][2].getFieldType());
        assertEquals(0, player.getResource(ResourceType.WOOD));
    }


    @Test
    @DisplayName("울타리 건설 - 어마무시한 거대 울타리: 성공")
    void test4() {
        // given
        player.addResource(ResourceType.WOOD, 14);
        List<BuildFenceActionExtensionRequest> requests = List.of(
                new BuildFenceActionExtensionRequest(0,1, false),
                new BuildFenceActionExtensionRequest(0,2, false),
                new BuildFenceActionExtensionRequest(0,3, false),
                new BuildFenceActionExtensionRequest(0,4, false),
                new BuildFenceActionExtensionRequest(3,1, false),
                new BuildFenceActionExtensionRequest(3,2, false),
                new BuildFenceActionExtensionRequest(3,3, false),
                new BuildFenceActionExtensionRequest(3,4, false),
                new BuildFenceActionExtensionRequest(0,1, true),
                new BuildFenceActionExtensionRequest(1,1, true),
                new BuildFenceActionExtensionRequest(2,1, true),
                new BuildFenceActionExtensionRequest(0,5, true),
                new BuildFenceActionExtensionRequest(1,5, true),
                new BuildFenceActionExtensionRequest(2,5, true)
        );

        // when
        action.runAction(player, requests);

        // then
        printPlayerBoard();
        assertTrue(player.getPlayerBoard().getRowFence()[0][1]);
        assertTrue(player.getPlayerBoard().getRowFence()[0][2]);
        assertTrue(player.getPlayerBoard().getRowFence()[0][3]);
        assertTrue(player.getPlayerBoard().getRowFence()[0][4]);
        assertTrue(player.getPlayerBoard().getRowFence()[3][1]);
        assertTrue(player.getPlayerBoard().getRowFence()[3][2]);
        assertTrue(player.getPlayerBoard().getRowFence()[3][3]);
        assertTrue(player.getPlayerBoard().getRowFence()[3][4]);

        assertTrue(player.getPlayerBoard().getColFence()[0][1]);
        assertTrue(player.getPlayerBoard().getColFence()[1][1]);
        assertTrue(player.getPlayerBoard().getColFence()[2][1]);
        assertTrue(player.getPlayerBoard().getColFence()[0][5]);
        assertTrue(player.getPlayerBoard().getColFence()[1][5]);
        assertTrue(player.getPlayerBoard().getColFence()[2][5]);
        assertEquals(FieldType.BARN, player.getPlayerBoard().getFields()[0][1].getFieldType());
        assertEquals(FieldType.BARN, player.getPlayerBoard().getFields()[0][2].getFieldType());
        assertEquals(FieldType.BARN, player.getPlayerBoard().getFields()[0][3].getFieldType());
        assertEquals(FieldType.BARN, player.getPlayerBoard().getFields()[1][1].getFieldType());
        assertEquals(FieldType.BARN, player.getPlayerBoard().getFields()[1][2].getFieldType());
        assertEquals(FieldType.BARN, player.getPlayerBoard().getFields()[1][3].getFieldType());
        assertEquals(FieldType.BARN, player.getPlayerBoard().getFields()[2][1].getFieldType());
        assertEquals(FieldType.BARN, player.getPlayerBoard().getFields()[2][2].getFieldType());
        assertEquals(FieldType.BARN, player.getPlayerBoard().getFields()[2][3].getFieldType());
        assertEquals(0, player.getResource(ResourceType.WOOD));
    }


    @Test
    @Disabled
    @DisplayName("울타리 건설 - 15개 초과 사용: 실패")
    void test6() {
        // given
        player.addResource(ResourceType.WOOD, 17);
        List<BuildFenceActionExtensionRequest> requests = List.of(
                new BuildFenceActionExtensionRequest(0,1, false),
                new BuildFenceActionExtensionRequest(0,2, false),
                new BuildFenceActionExtensionRequest(0,3, false),
                new BuildFenceActionExtensionRequest(0,4, false),
                new BuildFenceActionExtensionRequest(3,1, false),
                new BuildFenceActionExtensionRequest(3,2, false),
                new BuildFenceActionExtensionRequest(3,3, false),
                new BuildFenceActionExtensionRequest(3,4, false),
                new BuildFenceActionExtensionRequest(0,1, true),
                new BuildFenceActionExtensionRequest(1,1, true),
                new BuildFenceActionExtensionRequest(2,1, true),
                new BuildFenceActionExtensionRequest(0,5, true),
                new BuildFenceActionExtensionRequest(1,5, true),
                new BuildFenceActionExtensionRequest(2,5, true)
        );

        List<BuildFenceActionExtensionRequest> request2 = List.of(
                new BuildFenceActionExtensionRequest(0,0, false),
                new BuildFenceActionExtensionRequest(1,0, false),
                new BuildFenceActionExtensionRequest(0,0, true)
        );

        // when
        action.runAction(player, requests);
        printPlayerBoard();

        assertThrows(
                IllegalRequestException.class, () -> {
                    action.runAction(player, request2);
                    printPlayerBoard();
                }
        );

        // then
        assertFalse(player.getPlayerBoard().getRowFence()[0][0]);
        assertFalse(player.getPlayerBoard().getRowFence()[1][0]);
        assertFalse(player.getPlayerBoard().getColFence()[0][0]);
    }

    @Test
    @DisplayName("부적절한 울타리 건설: 실패")
    void test7() {
        // given
        player.addResource(ResourceType.WOOD, 1);
        List<BuildFenceActionExtensionRequest> requests = List.of(
                new BuildFenceActionExtensionRequest(0,1, false)
        );

        // when

        assertThrows(
                IllegalRequestException.class, () -> {
                    action.runAction(player, requests);
                    printPlayerBoard();
                }
        );

        // then
        assertFalse(player.getPlayerBoard().getRowFence()[0][1]);
    }

    @Test
    @DisplayName("부적절한 울타리 건설 - 건물 등지기: 실패")
    void test8() {
        // given
        player.addResource(ResourceType.WOOD, 3);
        List<BuildFenceActionExtensionRequest> requests = List.of(
                new BuildFenceActionExtensionRequest(0,0, false),
                new BuildFenceActionExtensionRequest(0,0, true),
                new BuildFenceActionExtensionRequest(0,1, true)
        );

        // when
        assertThrows(
                IllegalRequestException.class, () -> {
                    action.runAction(player, requests);
                    printPlayerBoard();
                }
        );

        // then
        assertFalse(player.getPlayerBoard().getRowFence()[0][0]);
        assertFalse(player.getPlayerBoard().getColFence()[0][0]);
        assertFalse(player.getPlayerBoard().getColFence()[0][1]);
    }


    @Test
    @DisplayName("분리된 울타리 건설 : 실패")
    void test9() {
        // given
        player.addResource(ResourceType.WOOD, 10);
        List<BuildFenceActionExtensionRequest> requests = List.of(
                new BuildFenceActionExtensionRequest(0,0, false),
                new BuildFenceActionExtensionRequest(1,0, false),
                new BuildFenceActionExtensionRequest(0,0, true),
                new BuildFenceActionExtensionRequest(0,1, true)
        );

        List<BuildFenceActionExtensionRequest> request2 = List.of(
                new BuildFenceActionExtensionRequest(0,2, false),
                new BuildFenceActionExtensionRequest(1,2, false),
                new BuildFenceActionExtensionRequest(0,2, true),
                new BuildFenceActionExtensionRequest(0,3, true)
        );


        // when
        action.runAction(player, requests);
        printPlayerBoard();
        assertThrows(
                IllegalRequestException.class, () -> {
                    action.runAction(player, request2);
                    printPlayerBoard();
                }
        );

        // then
        assertFalse(player.getPlayerBoard().getRowFence()[0][2]);
        assertFalse(player.getPlayerBoard().getRowFence()[1][2]);
        assertFalse(player.getPlayerBoard().getColFence()[0][2]);
        assertFalse(player.getPlayerBoard().getColFence()[0][3]);
    }

    @Test
    @DisplayName("울타리 분리하기 : 성공")
    void test10() {
        // given
        player.addResource(ResourceType.WOOD, 10);
        List<BuildFenceActionExtensionRequest> requests = List.of(
                new BuildFenceActionExtensionRequest(0,0, false),
                new BuildFenceActionExtensionRequest(0,1, false),
                new BuildFenceActionExtensionRequest(1,0, false),
                new BuildFenceActionExtensionRequest(1,1, false),
                new BuildFenceActionExtensionRequest(0,0, true),
                new BuildFenceActionExtensionRequest(0,2, true)
        );

        List<BuildFenceActionExtensionRequest> request2 = List.of(
                new BuildFenceActionExtensionRequest(0,1, true)
        );


        // when
        action.runAction(player, requests);
        printPlayerBoard();
        action.runAction(player, request2);
        printPlayerBoard();

        // then
    }

    @Test
    @DisplayName("마구간 설치 되어 있는 곳에 울타리 설치 : 성공")
    void test11() {
        // given
        player.buildField(2,2,FieldType.STABLE);
        player.addResource(ResourceType.WOOD, 10);
        List<BuildFenceActionExtensionRequest> requests = List.of(
                new BuildFenceActionExtensionRequest(2,2, false),
                new BuildFenceActionExtensionRequest(3,2, false),
                new BuildFenceActionExtensionRequest(2,2, true),
                new BuildFenceActionExtensionRequest(2,3, true)
        );


        // when
        action.runAction(player, requests);
        printPlayerBoard();

        // then
        assertTrue(player.getPlayerBoard().getRowFence()[2][2]);
        assertTrue(player.getPlayerBoard().getRowFence()[3][2]);
        assertTrue(player.getPlayerBoard().getColFence()[2][2]);
        assertTrue(player.getPlayerBoard().getColFence()[2][3]);
    }
}