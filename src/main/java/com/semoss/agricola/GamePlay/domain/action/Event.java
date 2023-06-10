package com.semoss.agricola.GamePlay.domain.action;

import com.fasterxml.jackson.annotation.*;
import com.semoss.agricola.GamePlay.domain.History;
import com.semoss.agricola.GamePlay.domain.action.component.ActionType;
import com.semoss.agricola.GamePlay.domain.action.component.StackAnimalAction;
import com.semoss.agricola.GamePlay.domain.action.component.StackResourceAction;
import com.semoss.agricola.GamePlay.domain.action.implement.DefaultAction;
import com.semoss.agricola.GamePlay.domain.card.CardDictionary;
import com.semoss.agricola.GamePlay.domain.player.Player;
import com.semoss.agricola.GamePlay.domain.resource.AnimalStruct;
import com.semoss.agricola.GamePlay.domain.resource.ResourceStruct;
import com.semoss.agricola.GamePlay.domain.resource.ResourceStructInterface;
import com.semoss.agricola.GamePlay.dto.AgricolaActionRequest;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Event {
    @JsonUnwrapped
    private final DefaultAction action;
    private final List<ResourceStructInterface> stacks = new ArrayList<>(); // 누적 쌓인 자원

    @Setter
    private int round;

    @JsonProperty("playerId")
    @JsonIdentityReference(alwaysAsId = true)
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "userId")
    private Player isPlayed;

    @Builder
    public Event(DefaultAction action) {
        this.action = action;
        this.isPlayed = null;
    }

    /**
     * 입력에 대해 액션 수행
     * @param player 액션을 플레이하는 플레이어
     * @param acts 액션 수행관련 세부 사항
     */
    public History runActions(Player player, Player startingPlayer, int gameRound, List<AgricolaActionRequest.ActionFormat> acts, CardDictionary cardDictionary) {
        // 이미 플레이된 상태인지 확인
        if(this.isPlayed != null)
            throw new RuntimeException("이미 플레이한 액션칸입니다.");

        History history = History.builder()
                .eventName(this.action.getEventName())
                .build();

        this.action.runAction(player, startingPlayer, gameRound, acts, this.stacks, cardDictionary, history);

        this.isPlayed = player;

        return history;
    }
    /**
     * 누적 액션을 실행하여 자원을 행동칸에 쌓는다.
     */
    public void processStackEvent(){
        action.getActions().stream()
                .filter(action -> action.getActionType() == ActionType.STACK)
                .map(action -> ((StackResourceAction) action).getStackResource())
                .forEach(this::addStackResource);

        action.getActions().stream()
                .filter(action -> action.getActionType() == ActionType.STACK_ANIMAL)
                .map(action -> ((StackAnimalAction) action).getStackAnimal())
                .forEach(this::stackAnimal);
    }

    /**
     * 스택 개수만큼 자원을 행동칸에 쌓는다.
     * @param resource 쌓을 자원 스택
     */
    private void addStackResource(ResourceStruct resource) {
        // 이미 쌓인 자원인 경우 찾아서 더하고, 없는 경우 새로 추가한다.
        stacks.stream()
                .filter(ResourceStructInterface::isResource)
                .map(resourceStructInterface -> (ResourceStruct) resourceStructInterface)
                .filter(stack -> stack.getResource().equals(resource.getResource()))
                .findFirst()
                .ifPresentOrElse(
                        stack -> stack.addResource(resource.getCount()),
                        () -> stacks.add(resource)
                );
    }

    /**
     * 스택 개수만큼 동물을 행동칸에 쌓는다.
     * @param animal 쌓을 동물 스택
     */
    public void stackAnimal(AnimalStruct animal) {
        // 이미 쌓인 가축인 경우 찾아서 더하고, 없는 경우 새로 추가한다.
        stacks.stream()
                .filter(ResourceStructInterface::isResource)
                .map(resourceStructInterface -> (AnimalStruct) resourceStructInterface)
                .filter(stack -> stack.getAnimal().equals(animal.getAnimal()))
                .findFirst()
                .ifPresentOrElse(
                        stack -> stack.addResource(animal.getCount()),
                        () -> stacks.add(animal)
                );
    }

    /**
     * 플레이 여부 초기화
     */
    public void initPlayed() {
        this.isPlayed = null;
    }
}
