package com.semoss.agricola.GamePlay.domain.action.component;

import com.semoss.agricola.GamePlay.domain.resource.AnimalStruct;
import lombok.Builder;
import lombok.Getter;

/**
 * 자원이 누적해서 쌓이는 행동
 */
public class StackAnimalAction implements StackAction {
    @Getter
    private final ActionType actionType = ActionType.STACK_ANIMAL;
    @Getter
    private final AnimalStruct stackAnimal;

    /**
     * 동물 쌓기 액션, 라운드 마다 동물을 추가로 쌓는다.
     * @param animal  스택할 동물 종류와 개수
     */
    @Builder
    public StackAnimalAction(AnimalStruct animal) {
        this.stackAnimal = animal;
    }
}

