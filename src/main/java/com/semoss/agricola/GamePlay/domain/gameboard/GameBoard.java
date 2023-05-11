package com.semoss.agricola.GamePlay.domain.gameboard;

import com.semoss.agricola.GamePlay.domain.action.*;
import com.semoss.agricola.GamePlay.domain.player.FieldType;
import com.semoss.agricola.GamePlay.domain.resource.ResourceStruct;
import com.semoss.agricola.GamePlay.domain.resource.ResourceType;
import lombok.Builder;
import lombok.Getter;

import java.util.*;

/**
 * 아그리콜라 메인 게임 보드
 */
@Getter
public class GameBoard {
    private List<Event> events;
    private ImprovementBoard improvementBoard;

    @Builder
    public GameBoard() {
        // event를 배치한다.
        events = new ArrayList<>();
        // 1.방 만들기 그리고/또는 외양간 짓기
        List<Action> actions1 = new ArrayList<>();
        // 1-1. 방만들기
        List<ResourceStruct> requirements1_1 = new ArrayList<>();
        requirements1_1.add(ResourceStruct.builder()
                .resource(ResourceType.WOOD)
                .count(5)
                .build());
        requirements1_1.add(ResourceStruct.builder()
                .resource(ResourceType.GRAIN)
                .count(2)
                .build());
        actions1.add(BuildSimpleAction.builder()
                        .fieldType(FieldType.ROOM)
                        .buildMaxCount(-1)
                        .requirements(requirements1_1)
                .build());
        // 1-2 외양간 짓기
        List<ResourceStruct> requirements1_2 = new ArrayList<>();
        requirements1_2.add(ResourceStruct.builder()
                .resource(ResourceType.WOOD)
                .count(2)
                .build());
        actions1.add(BuildSimpleAction.builder()
                .fieldType(FieldType.STABLE)
                .buildMaxCount(-1)
                .requirements(requirements1_2)
                .build());
        events.add(Event.builder()
                .actions(actions1)
                .actionDoType(new ArrayList<>(Arrays.asList(DoType.ANDOR)))
                .round(0)
                .build());

        // TODO: 2.시작 플레이어 되기 그리고/또는 보조 설비 1개 놓기
        // 2-1. 시작 플레이어 되기
        List<Action> actions2 = new ArrayList<>();
        actions2.add(GetStartingPositionAction.builder().build());
        events.add(Event.builder()
                .actions(actions2)
                .actionDoType(new ArrayList<>())
                .round(0)
                .build());


        // 3.곡식 1개 가져가기
        List<Action> actions3 = new ArrayList<>();
        actions3.add(BasicAction.builder()
                .resource(ResourceStruct.builder()
                        .resource(ResourceType.GRAIN)
                        .count(1)
                        .build())
                .build());
        events.add(Event.builder()
                .actions(actions3)
                .actionDoType(new ArrayList<>())
                .round(0)
                .build());

        // TODO: 4.직업 1개 놓기

        // 5.밭 1개 일구기
        events.add(Event.builder()
                .actions(new ArrayList<>(Arrays.asList(buildActionToTillingFarm())))
                .actionDoType(new ArrayList<>())
                .round(0)
                .build());

        // 6.날품팔이 (식량 2개 가져가기)
        List<Action> actions6 = new ArrayList<>();
        actions6.add(BasicAction.builder()
                .resource(ResourceStruct.builder()
                        .resource(ResourceType.FOOD)
                        .count(2)
                        .build())
                .build());
        events.add(Event.builder()
                .actions(actions6)
                .actionDoType(new ArrayList<>())
                .round(0)
                .build());

        // 7.누적 나무 3개
        events.add(buildEventToSimpleStackAction(ResourceType.WOOD, 3, 0));

        // 8.누적 흙 1개
        events.add(buildEventToSimpleStackAction(ResourceType.STONE, 1, 0));

        // 9.누적 갈대 1개
        events.add(buildEventToSimpleStackAction(ResourceType.REED, 1, 0));

        // 10.낚시 (누적 음식 1개)
        events.add(buildEventToSimpleStackAction(ResourceType.FOOD, 1, 0));

        // --- 1주기 ---
        // 11. 양 시장 (누적 양 1개)
        events.add(buildEventToSimpleStackAction(ResourceType.SHEEP, 1, 1));

        // 12. 울타리 치기
        events.add(Event.builder()
                .actions(new ArrayList(Arrays.asList(buildActionToFence())))
                .actionDoType(new ArrayList<>())
                .round(1)
                .build());

        // TODO: 13. 주요 설비나 보조 설비 1개 놓기

        // 14. 씨 뿌리기 그리고/또는 빵 굽기
        events.add(Event.builder()
                .actions(new ArrayList<>(Arrays.asList(buildActionToCultivation(), buildActionToBake())))
                .actionDoType(new ArrayList<>(Arrays.asList(DoType.ANDOR)))
                .round(1)
                .build());

        //  --- 2주기 ---

        // TODO: 15. 집 개조: 집 한번 고치기 그 후에 주요 설비 / 보조 설비 중 1개 내려놓기

        // 16. 서부 채석장: 누적 돌 1개
        events.add(buildEventToSimpleStackAction(ResourceType.STONE, 1, 2));

        // TODO: 17. 급하지 않은 가족 늘리기: 빈 방이 있어야만 가족 늘리기 그 후에 보조 설비 1개
        List<Action> actions17 = new ArrayList<>();
        actions17.add(IncreaseFamily.builder()
                        .precondition(true)
                        .build());
        events.add(Event.builder()
                .actions(actions17)
                .actionDoType(new ArrayList<>())
                .round(2)
                .build());

        // --- 3주기 ---

        // 18.돼지 시장: 누적 돼지 1개
        events.add(buildEventToSimpleStackAction(ResourceType.WILD_BOAR, 1, 3));

        // 19.채소 종자: 채소 1개
        events.add(buildEventToSimpleStackAction(ResourceType.VEGETABLE, 1, 3));

        // --- 4주기 ---

        // 20.소 시장: 누적 소 1개
        events.add(buildEventToSimpleStackAction(ResourceType.CATTLE, 1, 4));

        // 21.동부 채석장: 누적 돌 1개
        events.add(buildEventToSimpleStackAction(ResourceType.STONE, 1, 4));

        // --- 5주기 ---

        // 22.밭 농사: 밭 하나 일구기 그리고/또는 씨 뿌리기
        events.add(Event.builder()
                .actions(new ArrayList<>(Arrays.asList(
                        buildActionToTillingFarm(),
                        buildActionToCultivation())))
                .actionDoType(new ArrayList<>(Arrays.asList(DoType.ANDOR)))
                .round(5)
                .build());

        // 23.급한 가족 늘리기: 빈 방이 없어도 가족 늘리기
        List<Action> actions23 = new ArrayList<>();
        actions23.add(IncreaseFamily.builder()
                .precondition(false)
                .build());
        events.add(Event.builder()
                .actions(actions23)
                .actionDoType(new ArrayList<>())
                .round(5)
                .build());

        // --- 6주기 ---

        // \24. 농장 개조: 집 한번 고치기 그 후에 울타리 치기
        events.add(Event.builder()
                .actions(new ArrayList<>(Arrays.asList(
                        buildActionToRoomUpgrade(),
                        buildActionToFence())))
                .actionDoType(new ArrayList<>(Arrays.asList(DoType.ANDOR)))
                .round(6)
                .build());

        // 같은 라운드 행동 이벤트끼리는 무작위로 섞는다.
        shuffleEventsWithinRound();

        // TODO : 주설비 보드판 제작
    }

    /**
     * 단일 stackAction을 구성한다.
     * @param resourceType
     * @param num
     * @param round
     * @return
     */
    private Event buildEventToSimpleStackAction(ResourceType resourceType, int num, int round) {
        List<Action> actions = new ArrayList<>();
        actions.add(StackAction.builder()
                .resourceType(resourceType)
                .stackCount(num)
                .build());
        return Event.builder()
                .actions(actions)
                .actionDoType(new ArrayList<>())
                .round(round)
                .build();
    }

    /**
     * 밭 일구기 액션를 구성한다.
     * @return
     */
    private BuildSimpleAction buildActionToTillingFarm() {
        return BuildSimpleAction.builder()
                .fieldType(FieldType.FARM)
                .buildMaxCount(1)
                .requirements(new ArrayList<>())
                .build();
    }

    /**
     * 씨 뿌리기 액션을 구성한다.
     * @return
     */
    private CultivationAction buildActionToCultivation() {
        return CultivationAction.builder()
                .build();
    }

    /**
     * 빵 굽기 액션을 구성한다.
     * @return
     */
    private BakeAction buildActionToBake() {
        return BakeAction.builder().build();
    }

    /**
     * 집 업그레이드 액션을 구성한다.
     * @return
     */
    private RoomUpgradeAction buildActionToRoomUpgrade() {
        Map<ResourceType, List<ResourceStruct>> costs = new HashMap<>();
        // 나무집 -> 흙집 업그레이드 비용
        costs.put(ResourceType.CLAY, new ArrayList<>(Arrays.asList(
                ResourceStruct.builder()
                        .resource(ResourceType.REED)
                        .count(1)
                        .build(), ResourceStruct.builder()
                        .resource(ResourceType.CLAY)
                        .count(1)
                        .build())));

        // 흙집 -> 돌집 업그레이드 비용
        costs.put(ResourceType.STONE, new ArrayList<>(Arrays.asList(
                ResourceStruct.builder()
                        .resource(ResourceType.REED)
                        .count(1)
                        .build(), ResourceStruct.builder()
                        .resource(ResourceType.STONE)
                        .count(1)
                        .build())));

        return RoomUpgradeAction.builder()
                .costs(costs)
                .build();
    }

    /**
     * 울타리 건설 액션을 구성한다.
     * @return
     */
    private BuildFenceAction buildActionToFence(){
        return BuildFenceAction.builder().build();
    }

    /**
     * 해당 인덱스 내에서 배열을 셔플하고 저장하는 함수
     * @param lst 셔플할 배열
     * @param start 셔플할 배열 시작 위치
     * @param end 셔플할 배열 끝 위치
     */
    private void shuffleByIndex(List<Event> lst, int start, int end) {
        // 셔플할 구간을 추출하여 별도의 리스트로 저장
        List<Object> subLst = new ArrayList<>(lst.subList(start, end));
        // 셔플을 수행
        Collections.shuffle(subLst);

        // 원래 리스트에 셔플된 구간을 삽입
        for (int i = 0; i < subLst.size(); i++) {
            lst.set(start + i, (Event) subLst.get(i));
        }
    }

    /**
     * 이벤트 객체 셔플
     */
    private void shuffleEventsWithinRound() {
        if (this.events == null || this.events.size() == 0)
            throw new RuntimeException("이벤트 객체가 비어 있습니다.");

        // round 값을 기준으로 이전과 다음 Event 객체의 round 값과 비교하기 위한 변수
        int prevRound = events.get(0).getRound();
        int startIndex = 0;
        int currRound;


        // round 값을 기준으로 정렬된 events 리스트에서 round 값이 같은 Event 객체들을 추출하여 셔플
        for (int i = 0; i < events.size(); i++) {
            currRound = events.get(i).getRound();

            // 셔플할 라운드가 0이 아니고, 이전 Event 객체의 round 값과 비교하여 다른 round 값이라면 셔플
            if (currRound != prevRound && prevRound != 0) {
                shuffleByIndex(this.events, startIndex, i - 1);
                prevRound = currRound;
                startIndex = i;
            }

        }
        shuffleByIndex(this.events, startIndex, events.size());
    }

}
