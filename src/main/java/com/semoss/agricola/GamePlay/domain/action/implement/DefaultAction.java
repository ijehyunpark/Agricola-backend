package com.semoss.agricola.GamePlay.domain.action.implement;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.semoss.agricola.GamePlay.domain.History;
import com.semoss.agricola.GamePlay.domain.action.*;
import com.semoss.agricola.GamePlay.domain.card.Card;
import com.semoss.agricola.GamePlay.domain.card.CardDictionary;
import com.semoss.agricola.GamePlay.domain.card.MajorCard;
import com.semoss.agricola.GamePlay.domain.player.AnimalType;
import com.semoss.agricola.GamePlay.domain.player.FieldType;
import com.semoss.agricola.GamePlay.domain.player.Player;
import com.semoss.agricola.GamePlay.domain.resource.AnimalStruct;
import com.semoss.agricola.GamePlay.domain.resource.ResourceStruct;
import com.semoss.agricola.GamePlay.domain.resource.ResourceStructInterface;
import com.semoss.agricola.GamePlay.domain.resource.ResourceType;
import com.semoss.agricola.GamePlay.dto.AgricolaActionRequest;
import com.semoss.agricola.GamePlay.dto.BakeActionExtentionRequest;
import com.semoss.agricola.GamePlay.dto.BuildActionExtentionRequest;
import com.semoss.agricola.GamePlay.dto.CultivationActionExtentionRequest;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;


@Getter
public abstract class DefaultAction {
    private final EventName eventName;
    private final List<Action> actions = new ArrayList<>();
    private final List<DoType> doTypes = new ArrayList<>();
    @JsonIgnore
    private final int roundGroup;

    protected DefaultAction(EventName eventName, int roundGroup) {
        this.eventName = eventName;
        this.roundGroup = roundGroup;
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
     * 밭 일구기 액션를 구성한다.
     * @return 밭 일구기 건설 액션 객체를 반환한다.
     */
    protected BuildSimpleAction buildActionToTillingFarm() {
        return BuildSimpleAction.builder()
                .fieldType(FieldType.FARM)
                .buildMaxCount(1)
                .requirements(new ArrayList<>())
                .build();
    }

    /**
     * 단일 stackAction을 구성한다.
     * @param resourceType 누적할 자원 종류
     * @param num 누적할 자원 개수
     * @return 단일 누적 액션 객체를 반환한다.
     */
    protected Action buildSimpleStackAction(ResourceType resourceType, int num) {
        return StackResourceAction.builder()
                .resourceType(resourceType)
                .stackCount(num)
                .build();
    }

    /**
     * 단일 stackAnimalAction을 구성한다.
     * @param animalType 누적할 가축 종류
     * @param num 누적할 자원 개수
     * @return 단일 누적 액션 객체를 반환한다.
     */
    protected Action buildSimpleStackAction(AnimalType animalType, int num) {
        return StackAnimalAction.builder()
                .animalType(animalType)
                .stackCount(num)
                .build();
    }

    /**
     * 울타리 건설 액션을 구성한다.
     * @return 울타리 건설 액션 객체를 반환한다.
     */
    protected BuildFenceAction buildActionToFence() {
        return BuildFenceAction.builder().build();
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
        // TODO: Object 입력 개선 (object acts.acts)
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
                        BakeActionExtentionRequest request = (BakeActionExtentionRequest) act.getActs();
                        cardDictionary.getCard((long) request.getImprovmentId());
                        ((BakeAction) action).runAction(player, (MajorCard) act.getActs());
                    }
                    case BUILD -> {
                        BuildActionExtentionRequest request = (BuildActionExtentionRequest) act.getActs();
                        ((MultiInputAction) action).runAction(player, act.getActs());
                    }
                    case CULTIVATION -> {
                        CultivationActionExtentionRequest request = (CultivationActionExtentionRequest) act.getActs();
                        ((CultivationAction) action).runAction(player, request.getY(), request.getX(), request.getResourceType());
                    }
                    case PLACE -> {
                        Card card = cardDictionary.getCard((Long) act.getActs());
                        ((PlaceAction) action).runAction(player, card);
                    }
                    case STACK, STACK_ANIMAL -> {
                        player.addResource(stacks.stream()
                                .filter(ResourceStructInterface::isResource)
                                .map(resourceStructInterface -> (ResourceStruct) resourceStructInterface)
                                .toList());
                        player.addAnimal(stacks.stream()
                                .filter(ResourceStructInterface::isAnimal)
                                .map(resourceStructInterface -> (AnimalStruct) resourceStructInterface)
                                .toList());
                        history.writeResourceChange(stacks);
                        stacks.clear();
                    }
                }
                history.writeActionType(action.getActionType(), times);
            });
        return history;
    }
}
