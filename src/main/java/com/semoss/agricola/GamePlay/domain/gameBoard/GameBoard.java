package com.semoss.agricola.GamePlay.domain.gameBoard;

import com.semoss.agricola.GamePlay.domain.action.Event;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 아그리콜라 메인 게임 보드
 */
@Getter
public class GameBoard {
    private List<Event> events;
//    private ImprovementBoard improvementBoard;

    @Builder
    public GameBoard() {
        // TODO : 게임 보드 설계
        // event 배치 및 주설비 배치
        shuffleEventsWithinRound();
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

            // 이전 Event 객체의 round 값과 비교하여 다른 round 값이라면 셔플
            if (currRound != prevRound) {
                shuffleByIndex(this.events, startIndex, i - 1);
                prevRound = currRound;
                startIndex = i;
            }

        }
        shuffleByIndex(this.events, startIndex, events.size());
    }

}
