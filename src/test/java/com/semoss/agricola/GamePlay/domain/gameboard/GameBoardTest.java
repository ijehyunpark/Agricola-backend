package com.semoss.agricola.GamePlay.domain.gameboard;

import com.semoss.agricola.GamePlay.domain.AgricolaGame;
import com.semoss.agricola.GamePlay.domain.action.ActionFactory;
import com.semoss.agricola.GamePlay.domain.action.Event;
import com.semoss.agricola.GamePlay.domain.action.implement.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class GameBoardTest {
    AgricolaGame game;
    ImprovementBoard improvementBoard;
    GameBoard board;

    List<Event> buildEvents() {
        ActionFactory actionFactory = new ActionFactory();
        return List.of(
                Event.builder()
                        .action(new Action1(actionFactory.buildRoomAction(), actionFactory.buildStableAction()))
                        .build(),
                Event.builder()
                        .action(new Action2(actionFactory.getStartingPositionAction(), actionFactory.placeMinorCardAction()))
                        .build(),
                Event.builder()
                        .action(new Action3(actionFactory.getGrain1Action()))
                        .build(),
                Event.builder()
                        .action(new Action4(actionFactory.placeOccupationCardAction()))
                        .build(),
                Event.builder()
                        .action(new Action5(actionFactory.buildFarmAction()))
                        .build(),
                Event.builder()
                        .action(new Action6(actionFactory.datallerAction()))
                        .build(),
                Event.builder()
                        .action(new Action7(actionFactory.stackWood3Action()))
                        .build(),
                Event.builder()
                        .action(new Action8(actionFactory.stackClay1Action()))
                        .build(),
                Event.builder()
                        .action(new Action9(actionFactory.stackReed1Action()))
                        .build(),
                Event.builder()
                        .action(new Action10(actionFactory.fishingAction()))
                        .build(),
                Event.builder()
                        .action(new Action11(actionFactory.sheepMarketAction()))
                        .build(),
                Event.builder()
                        .action(new Action12(actionFactory.buildFenceAction()))
                        .build(),
                Event.builder()
                        .action(new Action13(actionFactory.placeMajorCardAction(), actionFactory.placeMinorCardAction()))
                        .build(),
                Event.builder()
                        .action(new Action14(actionFactory.cultivationAction(), actionFactory.bakeAction()))
                        .build(),
                Event.builder()
                        .action(new Action15(actionFactory.roomUpgradeAction(), actionFactory.placeMajorCardAction(), actionFactory.placeMinorCardAction()))
                        .build(),
                Event.builder()
                        .action(new Action16(actionFactory.westernQuarryAction()))
                        .build(),
                Event.builder()
                        .action(new Action17(actionFactory.growFamilyWithoutUrgencyAction(), actionFactory.placeMinorCardAction()))
                        .build(),
                Event.builder()
                        .action(new Action18(actionFactory.wildBoarMarketAction()))
                        .build(),
                Event.builder()
                        .action(new Action19(actionFactory.getVegetable1Action()))
                        .build(),
                Event.builder()
                        .action(new Action20(actionFactory.cattleMarketAction()))
                        .build(),
                Event.builder()
                        .action(new Action21(actionFactory.easternQuarryAction()))
                        .build(),
                Event.builder()
                        .action(new Action22(actionFactory.buildFarmAction(), actionFactory.cultivationAction()))
                        .build(),
                Event.builder()
                        .action(new Action23(actionFactory.growFamilyWithUrgencyAction()))
                        .build(),
                Event.builder()
                        .action(new Action24(actionFactory.roomUpgradeAction(), actionFactory.buildFenceAction()))
                        .build()
                );
    }

    @BeforeEach
    void setUp() {
        game = mock(AgricolaGame.class);
        improvementBoard = mock(ImprovementBoard.class);
        board = new GameBoard(buildEvents(), improvementBoard);
    }
    @Test
    @DisplayName("라운드 셔플 테스트")
    void shuffleEventsWithinRound_shouldShuffleEventsWithinSameRound() {
        // given
        List<Event> events = board.events();

        // when

        // then
        boolean isSorted = true;
        boolean isMatched = true;

        for (int i = 0; i < events.size() - 1; i++) {
            if (events.get(i).getRound() > events.get(i + 1).getRound() ||
                    events.get(i).getAction().getRoundGroup() > events.get(i + 1).getAction().getRoundGroup()) {
                isSorted = false;
            }
            if(events.get(i).getAction().getRoundGroup() == 0 && events.get(i).getRound() != 0 ||
                    events.get(i).getAction().getRoundGroup() != 0 && events.get(i).getRound() == 0){
                isMatched = false;
            }
            System.out.print("round: " + events.get(i).getRound() + "  ::  ");
            System.out.println("roundGroup: " + events.get(i).getAction().getRoundGroup());
        }
        System.out.print("round: " + events.get(events.size() - 1).getRound() + "  ::  ");
        System.out.println("roundGroup: " + events.get(events.size() - 1).getAction().getRoundGroup());
        // 정렬 되있는지 확인
        assertTrue(isSorted);
        // 라운드 배치가 되어 있는지 확인
        assertTrue(isMatched);
    }

    @Test
    @DisplayName("스택 이벤트 처리 테스트")
    void givenStackActions_whenProcessStackEvent_thenResourceAccumulated() {
        // given
        List<Event> events = board.events();

        // when
        board.processStackEvent();

        // then
        boolean dirtyChk = false;
        for(Event event : events){
            if (event.getStacks().size() != 0) {
                dirtyChk = true;
                break;
            }
        }
        assertTrue(dirtyChk);
    }

    @Test
    @Disabled
    @DisplayName("예약 자원 전달 테스트")
    void processReservationResourceTest() {
        // given

        // when

        // then

    }
}