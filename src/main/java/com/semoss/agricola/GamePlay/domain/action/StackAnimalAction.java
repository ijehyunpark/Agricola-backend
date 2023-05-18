package com.semoss.agricola.GamePlay.domain.action;

import com.semoss.agricola.GamePlay.domain.player.AnimalStruct;
import com.semoss.agricola.GamePlay.domain.player.AnimalType;
import com.semoss.agricola.GamePlay.domain.player.Player;
import lombok.Builder;
import lombok.Getter;

/**
 * 자원이 누적해서 쌓이는 행동
 */
public class StackAnimalAction implements SimpleAction {
    @Getter
    private final ActionType actionType = ActionType.STACK;
    @Getter
    private final AnimalStruct stackAnimal;

    /**
     * 동물 쌓기 액션, 라운드 마다 동물을 추가로 쌓는다.
     * @param animalType  동물 타입
     * @param stackCount 라운드당 쌓이는 동물 수
     */
    @Builder
    public StackAnimalAction(AnimalType animalType, int stackCount) {
        this.stackAnimal = AnimalStruct.builder()
                .animal(animalType)
                .count(stackCount)
                .build();
    }

    /**
     * 전제조건을 만족하는지 확인
     * @return always true
     */
    @Override
    public boolean checkPrecondition(Player player) {
        return true;
    }

    /**
     * stack resource action - do nothing, Stack actions do not do any additional work on player actions.
     * @param player player who get resource
     * @return always true
     */
    @Override
    public boolean runAction(Player player) {
        return true;
    }
}

