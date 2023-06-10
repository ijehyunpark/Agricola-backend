package com.semoss.agricola.GamePlay.domain.card.Occupation;

import com.semoss.agricola.GamePlay.domain.History;
import com.semoss.agricola.GamePlay.domain.action.component.ActionType;
import com.semoss.agricola.GamePlay.domain.card.ActionTrigger;
import com.semoss.agricola.GamePlay.domain.player.Player;
import com.semoss.agricola.GamePlay.domain.resource.ResourceType;
import com.semoss.agricola.util.Pair;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 특정 행동시 추가 자원 획득, TODO 빵굽기 설비 사용을 구현 못해서 미구현입니다. 대신 오류는 발생하지 않도록 아무 행동 안하게 해놨습니다.
 */
@Getter
@Component
public class MasterBaker extends DefaultOccupation implements ActionTrigger, ActionCrossTrigger {
    private final int playerRequirement;

    public MasterBaker(@Value("${masterBaker.id}") Long cardID,
                     @Value("${masterBaker.name}") String name,
                     @Value("${masterBaker.players}") Integer playerRequirement,
                     @Value("${masterBaker.description}") String description) {
        super(cardID, name, description);
        this.playerRequirement = playerRequirement;
    }

    @Override
    public void actionTrigger(Player player, History history){
        // 빵굽기 횟수만큼 곡식 추가
        int bakeCount = history.getActionTypesAndTimes().stream()
                .filter(actionTypeIntegerPair -> actionTypeIntegerPair.first() == ActionType.BAKE)
                .mapToInt(Pair::second)
                .sum();
        player.addResource(ResourceType.GRAIN, bakeCount);
    }

    @Override
    public void actionCrossTrigger(Player player, History history) {
        // TODO: 다른 사람 빵굽기 시 즉시 빵굽기 가능
    }

}
