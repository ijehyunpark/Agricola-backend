package com.semoss.agricola.GamePlay.domain.gameboard;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.semoss.agricola.GamePlay.domain.AgricolaGame;
import com.semoss.agricola.GamePlay.domain.History;
import com.semoss.agricola.GamePlay.domain.action.*;
import com.semoss.agricola.GamePlay.domain.card.CardDictionary;
import com.semoss.agricola.GamePlay.domain.card.CardType;
import com.semoss.agricola.GamePlay.domain.player.AnimalType;
import com.semoss.agricola.GamePlay.domain.player.FieldType;
import com.semoss.agricola.GamePlay.domain.player.Player;
import com.semoss.agricola.GamePlay.domain.resource.ResourceStruct;
import com.semoss.agricola.GamePlay.domain.resource.ResourceType;
import com.semoss.agricola.GamePlay.dto.AgricolaActionRequest;
import lombok.Builder;
import lombok.Getter;

import java.util.*;

/**
 * 아그리콜라 메인 게임 보드
 */
public class GameBoard {
    @JsonIgnore
    @Getter
    private AgricolaGame game;
    private final List<Event> events = new ArrayList<>();
    @Getter
    private ImprovementBoard improvementBoard;

    @Builder
    public GameBoard(AgricolaGame game, CardDictionary cardDictionary) {
        this.game = game;

        // TODO : OCP에 의해 외부에서 event 생성하여 사용하도록 변경
        // event를 배치한다.
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
                .gameBoard(this)
                .id(1)
                .actions(actions1)
                .actionDoType(new ArrayList<>(List.of(DoType.ANDOR)))
                .roundGroup(0)
                .build());

        // 2.시작 플레이어 되기 그리고/또는 보조 설비 1개 놓기
        // 2-1. 시작 플레이어 되기
        List<Action> actions2 = new ArrayList<>();
        actions2.add(GetStartingPositionAction.builder().build());
        // 2-1. 보조 설비 1개 놓기
        actions2.add(PlaceAction.builder()
                .cardType(CardType.MINOR)
                .build());
        events.add(Event.builder()
                .gameBoard(this)
                .id(2)
                .actions(actions2)
                .actionDoType(new ArrayList<>(Arrays.asList(DoType.ANDOR)))
                .roundGroup(0)
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
                .gameBoard(this)
                .id(3)
                .actions(actions3)
                .roundGroup(0)
                .build());

        // 4.직업 1개 놓기
        List<Action> actions4 = new ArrayList<>();
        actions4.add(PlaceAction.builder()
                .cardType(CardType.OCCUPATION)
                .build());
        events.add(Event.builder()
                .gameBoard(this)
                .id(4)
                .actions(actions4)
                .roundGroup(0)
                .build());

        // 5.밭 1개 일구기
        events.add(Event.builder()
                .gameBoard(this)
                .id(5)
                .actions(new ArrayList<>(Arrays.asList(buildActionToTillingFarm())))
                .roundGroup(0)
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
                .gameBoard(this)
                .id(6)
                .actions(actions6)
                .roundGroup(0)
                .build());

        // 7.누적 나무 3개
        events.add(buildEventToSimpleStackAction(ResourceType.WOOD, 3, 7, 0));

        // 8.누적 흙 1개
        events.add(buildEventToSimpleStackAction(ResourceType.STONE, 1, 8, 0));

        // 9.누적 갈대 1개
        events.add(buildEventToSimpleStackAction(ResourceType.REED, 1, 9, 0));

        // 10.낚시 (누적 음식 1개)
        events.add(buildEventToSimpleStackAction(ResourceType.FOOD, 1, 10,  0));

        // --- 1주기 ---
        // 11. 양 시장 (누적 양 1개)
        events.add(buildEventToSimpleStackAction(AnimalType.SHEEP, 1, 11, 1));

        // 12. 울타리 치기
        events.add(Event.builder()
                .gameBoard(this)
                .id(12)
                .actions(new ArrayList<>(Arrays.asList(buildActionToFence())))
                .roundGroup(1)
                .build());

        // 13. 주요 설비나 보조 설비 1개 놓기
        List<Action> action13 = new ArrayList<>();
        action13.add(PlaceAction.builder()
                .cardType(CardType.MAJOR)
                .build());
        action13.add(PlaceAction.builder()
                .cardType(CardType.MINOR)
                .build());
        events.add(Event.builder()
                .gameBoard(this)
                .id(13)
                .actions(action13)
                .actionDoType(new ArrayList(List.of(DoType.OR)))
                .roundGroup(1)
                .build());

        // 14. 씨 뿌리기 그리고/또는 빵 굽기
        events.add(Event.builder()
                .gameBoard(this)
                .id(14)
                .actions(new ArrayList<>(Arrays.asList(buildActionToCultivation(), buildActionToBake())))
                .actionDoType(new ArrayList<>(List.of(DoType.ANDOR)))
                .roundGroup(1)
                .build());

        //  --- 2주기 ---

        // 15. 집 개조: 집 한번 고치기 그 후에 주요 설비 / 보조 설비 중 1개 내려놓기
        List<Action> action15 = new ArrayList<>();
        action15.add(buildActionToRoomUpgrade());
        action15.add(PlaceAction.builder()
                .cardType(CardType.MAJOR)
                .build());
        action15.add(PlaceAction.builder()
                .cardType(CardType.MINOR)
                .build());
        events.add(Event.builder()
                .gameBoard(this)
                .id(15)
                .actions(action15)
                .actionDoType(new ArrayList<>(List.of(DoType.ANDOR, DoType.OR)))
                .roundGroup(2)
                .build());

        // 16. 서부 채석장: 누적 돌 1개
        events.add(buildEventToSimpleStackAction(ResourceType.STONE, 1, 16, 2));

        // 17. 급하지 않은 가족 늘리기: 빈 방이 있어야만 가족 늘리기 그 후에 보조 설비 1개
        List<Action> actions17 = new ArrayList<>();
        actions17.add(IncreaseFamily.builder()
                        .precondition(true)
                        .build());
        actions17.add(PlaceAction.builder()
                .cardType(CardType.MINOR)
                .build());
        events.add(Event.builder()
                .gameBoard(this)
                .id(17)
                .actions(actions17)
                .roundGroup(2)
                .build());

        // --- 3주기 ---

        // 18.돼지 시장: 누적 돼지 1개
        events.add(buildEventToSimpleStackAction(AnimalType.WILD_BOAR, 1, 18, 3));

        // 19.채소 종자: 채소 1개
        events.add(buildEventToSimpleStackAction(ResourceType.VEGETABLE, 1, 19, 3));

        // --- 4주기 ---

        // 20.소 시장: 누적 소 1개
        events.add(buildEventToSimpleStackAction(AnimalType.CATTLE, 1, 20, 4));

        // 21.동부 채석장: 누적 돌 1개
        events.add(buildEventToSimpleStackAction(ResourceType.STONE, 1, 21, 4));

        // --- 5주기 ---

        // 22.밭 농사: 밭 하나 일구기 그리고/또는 씨 뿌리기
        events.add(Event.builder()
                .gameBoard(this)
                .id(22)
                .actions(new ArrayList<>(Arrays.asList(
                        buildActionToTillingFarm(),
                        buildActionToCultivation())))
                .actionDoType(new ArrayList<>(List.of(DoType.ANDOR)))
                .roundGroup(5)
                .build());

        // 23.급한 가족 늘리기: 빈 방이 없어도 가족 늘리기
        List<Action> actions23 = new ArrayList<>();
        actions23.add(IncreaseFamily.builder()
                .precondition(false)
                .build());
        events.add(Event.builder()
                .gameBoard(this)
                .id(23)
                .actions(actions23)
                .roundGroup(5)
                .build());

        // --- 6주기 ---

        // 24. 농장 개조: 집 한번 고치기 그 후에 울타리 치기
        events.add(Event.builder()
                .gameBoard(this)
                .id(24)
                .actions(new ArrayList<>(Arrays.asList(
                        buildActionToRoomUpgrade(),
                        buildActionToFence())))
                .actionDoType(new ArrayList<>(List.of(DoType.ANDOR)))
                .roundGroup(6)
                .build());

        // 같은 라운드 행동 이벤트끼리는 무작위로 섞는다.
        shuffleEventsWithinRoundGroup();

        // 라운드를 지정한다.
        int round = 1;
        for (Event event : events) {
            event.setRound(event.getRoundGroup() == 0 ? 0 : round++);
        }

        // 주설비 보드판 제작
        improvementBoard = new ImprovementBoard(cardDictionary);
    }

    /**
     * 단일 stackAction을 구성한다.
     * @param resourceType
     * @param num
     * @param eventId
     * @param roundGroup
     * @return
     */
    private Event buildEventToSimpleStackAction(ResourceType resourceType, int num, int eventId, int roundGroup) {
        List<Action> actions = new ArrayList<>();
        actions.add(StackResourceAction.builder()
                .resourceType(resourceType)
                .stackCount(num)
                .build());
        return Event.builder()
                .gameBoard(this)
                .id(eventId)
                .actions(actions)
                .roundGroup(roundGroup)
                .build();
    }

    private Event buildEventToSimpleStackAction(AnimalType animalType, int num, int eventId, int roundGroup) {
        List<Action> actions = new ArrayList<>();
        actions.add(StackAnimalAction.builder()
                .animalType(animalType)
                .stackCount(num)
                .build());
        return Event.builder()
                .gameBoard(this)
                .id(eventId)
                .actions(actions)
                .roundGroup(roundGroup)
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

    public List<Event> getEvents() {
        return events.stream()
                .filter(event -> event.getRound() <= game.getRound())
                .toList();
    }

    /**
     * round group을 기준으로 해당 인덱스 내에서 배열을 셔플한다.
     * round group이 0인 경우 셔플 대상에서 제외된다.
     * round group이 0이 아닌 경우 같은 round group까리 셔플된다.
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
     * roundGroup을 기준으로 이벤트 객체를 셔플한다.
     */
    private void shuffleEventsWithinRoundGroup() {
        if (this.events.size() == 0)
            throw new RuntimeException("이벤트 객체가 비어 있습니다.");

        // roundGroup 값을 기준으로 이전과 다음 Event 객체의 round 값과 비교하기 위한 변수
        int prevRound = events.get(0).getRoundGroup();
        int startIndex = 0;
        int currRound;


        // roundGroup 값을 기준으로 정렬된 events 리스트에서 roundGroup 값이 같은 Event 객체들을 추출하여 셔플
        for (int i = 0; i < events.size(); i++) {
            currRound = events.get(i).getRoundGroup();

            // 셔플할 라운드가 0이 아니고, 이전 Event 객체의 roundGroup 값과 비교하여 다른 roundGroup 값이라면 셔플
            if (currRound != prevRound) {
                if(prevRound != 0){
                    shuffleByIndex(this.events, startIndex, i - 1);
                }
                prevRound = currRound;
                startIndex = i;
            }

        }
        shuffleByIndex(this.events, startIndex, events.size());
    }

    /**
     * 현재 필드의 모든 누적 액션의 누적자원량을 증가시킨다.
     */
    public void processStackEvent() {
        for(Event event : getEvents()){
            // 해당 이벤트의 모든 스택 액에 대해 자원을 쌓는다.
            event.getActions().stream()
                    .filter(action -> action.getActionType() == ActionType.STACK)
                    .map(action -> ((StackResourceAction) action).getStackResource())
                    .forEach(event::stackResource);
        }
    }

    /**
     * 해당 라운드에 해당하는 이벤트에 쌓인 예약 자원을 플레이어에게 전달한다.
     * @param round
     */
    public void processReservationResource(int round) {
        this.events.stream()
                .filter(event -> event.getRound() == round)
                .findAny()
                .ifPresent(Event::deliverReservation);
    }
    /**
     * 액션을 플레이한다.
     * @param player 액션을 플레이하는 플레이어
     * @param eventId 플레이할 액션
     * @param acts    액션에 필요한 추가 요청
     */
    public History playAction(Player player, Long eventId, List<AgricolaActionRequest.ActionFormat> acts) {
        return events.stream()
                .filter(event -> event.getEventName().getId() == eventId.intValue())
                .findAny()
                .orElseThrow(() -> new RuntimeException("이벤트가 존재하지 않습니다"))
                .runActions(player, acts);
    }

    /**
     * 이벤트의 플레이 여부를 초기화한다.
     */
    public void initPlayed() {
        events.stream()
                .forEach(Event::initPlayed);
    }
}
