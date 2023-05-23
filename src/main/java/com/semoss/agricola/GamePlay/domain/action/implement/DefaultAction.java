package com.semoss.agricola.GamePlay.domain.action.implement;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.semoss.agricola.GamePlay.domain.History;
import com.semoss.agricola.GamePlay.domain.action.*;
import com.semoss.agricola.GamePlay.domain.card.Card;
import com.semoss.agricola.GamePlay.domain.card.CardDictionary;
import com.semoss.agricola.GamePlay.domain.card.CardType;
import com.semoss.agricola.GamePlay.domain.card.MajorCard;
import com.semoss.agricola.GamePlay.domain.player.Player;
import com.semoss.agricola.GamePlay.domain.resource.ResourceStructInterface;
import com.semoss.agricola.GamePlay.dto.AgricolaActionRequest;
import com.semoss.agricola.GamePlay.dto.BakeActionExtensionRequest;
import com.semoss.agricola.GamePlay.dto.BuildActionExtensionRequest;
import com.semoss.agricola.GamePlay.dto.CultivationActionExtensionRequest;
import com.semoss.agricola.util.ObjectMapperSingleton;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;

import java.util.ArrayList;
import java.util.List;


@Getter
@JsonPropertyOrder({"id", "name", "actions", "doTypes"})
@Log4j2
public abstract class DefaultAction {
    @JsonIgnore
    private final ActionName eventName;
    private final List<Action> actions = new ArrayList<>();
    private final List<DoType> doTypes = new ArrayList<>();

    @JsonIgnore
    private final int roundGroup;

    protected DefaultAction(ActionName eventName, int roundGroup) {
        this.eventName = eventName;
        this.roundGroup = roundGroup;
    }

    public int getId() {
        return eventName.getId();
    }

    public String getName() {
        return eventName.getName();
    }

    /**
     * 새로운 액션을 추가한다.
     * @param action 추가할 액션
     * @param doType 다음 액션과의 관계
     */
    protected void addAction(Action action, DoType doType) {
        this.actions.add(action);
        this.doTypes.add(doType);
    }


    /**
     * 올바른 행동 입력인지 검증
     * @param acts 요청 필드
     */
    private void isCollectRequest(List<AgricolaActionRequest.ActionFormat> acts) {
        int actionSize = actions.size();
        for (int i = 0; i < actionSize; i++) {
            DoType doType = this.doTypes.get(i);
            // After 구문은 전 구문이 성립되야 이후 구문을 사용할 수 있습니다.
            if(doType == DoType.AFTER && !acts.get(i).getUse()) {
                for(int j = i + 1; j < actionSize; j++) {
                    if(acts.get(i).getUse())
                        throw new RuntimeException("액션 구분 오류");
                }
            }

            // OR 구문은 하나만 선택되어야 합니다.
            if(doType == DoType.OR){
                boolean chk = false;
                while(i < actionSize && this.doTypes.get(i) == DoType.OR) {
                    if(acts.get(i++).getUse()){
                        if(chk)
                            throw new RuntimeException("액션 구분 오류");
                        chk = true;
                    }
                }
            }
        }
    }


    /**
     * 입력에 대해 액션 수행
     * @param player 액션을 플레이하는 플레이어
     * @param acts  액션 수행관련 세부 사항
     * @param stacks 이벤트에 쌓인 스택
     * @param cardDictionary 카드 검색 디렉토리
     * @param history 행동 기록 객체
     * @return 해당 행동을 수행한 후 해당 기록을 반환
     */
    public History runAction(Player player, List<AgricolaActionRequest.ActionFormat> acts, List<ResourceStructInterface> stacks, CardDictionary cardDictionary, History history) {
        // 입력 행동값 검증
        isCollectRequest(acts);

        // ToDO: Memento로 실패 시 롤백


        this.actions.stream()
            .filter(action -> acts.get(actions.indexOf(action)).getUse())
            .forEach(action -> {
                AgricolaActionRequest.ActionFormat act = acts.get(actions.indexOf(action));
                Integer times = 1; // TODO 액션 횟수 요청 정의 후 해당 값 바인딩
                switch (action.getActionType()) {
                    case BASIC, STARTING, UPGRADE, ADOPT -> {
                        ((SimpleAction) action).runAction(player, history);
                    }
                    case BAKE -> {
                        try {
                            ObjectMapper objectMapper = ObjectMapperSingleton.getInstance();
                            String jsonString = objectMapper.writeValueAsString(act.getActs());
                            BakeActionExtensionRequest request = objectMapper.readValue(jsonString, BakeActionExtensionRequest.class);
                            request.getImprovementIds().forEach(
                                            immprovmentId -> {
                                                Card card = cardDictionary.getCard(immprovmentId);
                                                if (card.getCardType() != CardType.MAJOR)
                                                    throw new RuntimeException("주설비카드가 아닙니다.");
                                                ((BakeAction) action).runAction(player, (MajorCard) card);
                                            }
                                    );
                        } catch (JsonProcessingException e) {
                            throw new RuntimeException("요청 필드 오류");
                        }
                    }
                    case BUILD -> {
                        try {
                            ObjectMapper objectMapper = ObjectMapperSingleton.getInstance();
                            String jsonString = objectMapper.writeValueAsString(act.getActs());
                            BuildActionExtensionRequest request = objectMapper.readValue(jsonString, BuildActionExtensionRequest.class);
                            ((BuildSimpleAction) action).runAction(player, request.getY(), request.getX());
                        } catch (JsonProcessingException e) {
                            throw new RuntimeException("요청 필드 오류");
                        }
                    }
                    case BUILD_ROOM -> {
                        try {
                            ObjectMapper objectMapper = ObjectMapperSingleton.getInstance();
                            String jsonString = objectMapper.writeValueAsString(act.getActs());
                            BuildActionExtensionRequest request = objectMapper.readValue(jsonString, BuildActionExtensionRequest.class);
                            ((BuildRoomAction) action).runAction(player, request.getY(), request.getX());
                        } catch (JsonProcessingException e) {
                            throw new RuntimeException("요청 필드 오류");
                        }
                    }
                    case CULTIVATION -> {
                        try {
                            ObjectMapper objectMapper = ObjectMapperSingleton.getInstance();
                            String jsonString = objectMapper.writeValueAsString(act.getActs());
                            JavaType listType = objectMapper.getTypeFactory().constructCollectionType(List.class, CultivationActionExtensionRequest.class);
                            List<CultivationActionExtensionRequest> requestList = objectMapper.readValue(jsonString, listType);
                            requestList.forEach(request -> ((CultivationAction) action).runAction(player, request.getY(), request.getY(), request.getResourceType()));
                        } catch (JsonProcessingException e) {
                            throw new RuntimeException("요청 필드 오류");
                        }
                    }
                    case PLACE -> {
                        Card card = cardDictionary.getCard((Long) act.getActs());
                        ((PlaceAction) action).runAction(player, card);
                    }
                    case STACK, STACK_ANIMAL -> {
                        ((StackAction) action).runstackAction(player, stacks);
                        history.writeResourceChange(stacks);
                        stacks.clear();
                    }
                }
                history.writeActionType(action.getActionType(), times);
            });
        return history;
        }
}