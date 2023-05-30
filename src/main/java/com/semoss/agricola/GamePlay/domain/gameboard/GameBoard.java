package com.semoss.agricola.GamePlay.domain.gameboard;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.semoss.agricola.GamePlay.domain.History;
import com.semoss.agricola.GamePlay.domain.action.ActionType;
import com.semoss.agricola.GamePlay.domain.action.Event;
import com.semoss.agricola.GamePlay.domain.action.StackAnimalAction;
import com.semoss.agricola.GamePlay.domain.action.StackResourceAction;
import com.semoss.agricola.GamePlay.domain.card.CardDictionary;
import com.semoss.agricola.GamePlay.domain.player.Player;
import com.semoss.agricola.GamePlay.dto.AgricolaActionRequest;
import com.semoss.agricola.GamePlay.exception.NotFoundException;
import com.semoss.agricola.GamePlay.exception.ServerError;
import lombok.Builder;
import lombok.extern.log4j.Log4j2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * 아그리콜라 메인 게임 보드
 */
@Log4j2
public record GameBoard(@JsonIgnore List<Event> events, ImprovementBoard improvementBoard) {
    @Builder
    public GameBoard(List<Event> events, ImprovementBoard improvementBoard) {
        log.debug("Game Board가 생성되었습니다." + this.hashCode());

        this.events = new ArrayList<>(events);
        this.events.sort(Comparator.comparingInt(o -> o.getAction().getRoundGroup()));
        shuffleEventsWithinRoundGroup();
        int round = 1;
        for (Event event : this.events) {
            if (event.getAction().getRoundGroup() != 0)
                event.setRound(round++);
        }
        this.improvementBoard = improvementBoard;
    }

    /**
     * round group을 기준으로 해당 인덱스 내에서 배열을 셔플한다.
     * round group이 0인 경우 셔플 대상에서 제외된다.
     * round group이 0이 아닌 경우 같은 round group까리 셔플된다.
     *
     * @param lst   셔플할 배열
     * @param start 셔플할 배열 시작 위치
     * @param end   셔플할 배열 끝 위치
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
            throw new ServerError("이벤트 객체가 비어 있습니다.");

        // roundGroup 값을 기준으로 이전과 다음 Event 객체의 round 값과 비교하기 위한 변수
        int prevRound = events.get(0).getAction().getRoundGroup();
        int startIndex = 0;
        int currRound;


        // roundGroup 값을 기준으로 정렬된 events 리스트에서 roundGroup 값이 같은 Event 객체들을 추출하여 셔플
        for (int i = 0; i < events.size(); i++) {
            currRound = events.get(i).getAction().getRoundGroup();

            // 셔플할 라운드가 0이 아니고, 이전 Event 객체의 roundGroup 값과 비교하여 다른 roundGroup 값이라면 셔플
            if (currRound != prevRound) {
                if (prevRound != 0) {
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
        for (Event event : events()) {
            // 해당 이벤트의 모든 스택 액에 대해 자원을 쌓는다.
            event.getAction().getActions().stream()
                    .filter(action -> action.getActionType() == ActionType.STACK)
                    .map(action -> ((StackResourceAction) action).getStackResource())
                    .forEach(event::stackResource);

            event.getAction().getActions().stream()
                    .filter(action -> action.getActionType() == ActionType.STACK_ANIMAL)
                    .map(action -> ((StackAnimalAction) action).getStackAnimal())
                    .forEach(event::stackAnimal);
        }
    }

    /**
     * 해당 라운드에 해당하는 이벤트에 쌓인 예약 자원을 플레이어에게 전달한다.
     *
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
     *
     * @param player  액션을 플레이하는 플레이어
     * @param eventId 플레이할 액션
     * @param acts    액션에 필요한 추가 요청
     */
    public History playAction(Player player, Long eventId, List<AgricolaActionRequest.ActionFormat> acts, CardDictionary cardDictionary) {
        return events.stream()
                .filter(event -> event.getAction().getEventName().getId() == eventId.intValue())
                .findAny()
                .orElseThrow(() -> new NotFoundException("이벤트가 존재하지 않습니다"))
                .runActions(player, acts, cardDictionary);
    }

    /**
     * 이벤트의 플레이 여부를 초기화한다.
     */
    public void initPlayed() {
        events.forEach(Event::initPlayed);
    }
}
