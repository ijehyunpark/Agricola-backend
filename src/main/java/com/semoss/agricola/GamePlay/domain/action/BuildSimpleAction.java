package com.semoss.agricola.GamePlay.domain.action;

import ch.qos.logback.core.joran.sanity.Pair;
import com.semoss.agricola.GamePlay.domain.player.FieldType;
import com.semoss.agricola.GamePlay.domain.player.Player;
import com.semoss.agricola.GamePlay.domain.resource.ResourceStruct;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

/**
 * 단순 필드 건설 액션
 * 1개의 빈 필드에 새로운 건축물을 건설한다.
 * e.g 밭 일구기, 집 건설
 */
@Getter
public class BuildSimpleAction implements MultiInputAction {
    private final ActionType actionType = ActionType.BUILD;
    private final FieldType fieldType;
    private final List<ResourceStruct> requirements;
    private final int buildMaxCount;

    @Builder
    public BuildSimpleAction(FieldType fieldType, List<ResourceStruct> requirements, int buildMaxCount) {
        this.fieldType = fieldType;
        this.requirements = requirements;
        // 최대 건설 횟수: -1인 경우 무한
        this.buildMaxCount = buildMaxCount;
    }

    /**
     * 건설이 필요한 자원을 가지고 있는지 검사한다.
     * @param player 해당 행동을 수행할 플레이어
     * @return 플레이어가 필요한 자원을 가지고 있다면 true를 반환한다.
     */
    public boolean checkPrecondition(Player player, Object detail) {
        // 요구사항이 없을 경우 true 반환
        if (this.requirements.size() == 0)
            return true;

        // 플레이어가 요구 자원을 가지고 있는지 반환
        for (ResourceStruct requirement : requirements){
            if(!(player.getResource(requirement.getResource()) >= requirement.getCount()))
                return false;
        }
        return true;
    }

    /**
     * 플레이어의 필드에 새로운 건물을 추가한다.
     * @param player 건설 작업을 수행할 플레이어
     * @return ?
     */
    public void runAction(Player player, Object detail) {
        if(!checkPrecondition(player, detail))
            throw new RuntimeException("건설을 수행할 자원이 부족합니다.");

        try{
            if(!(detail instanceof Pair<?,?>))
                throw new RuntimeException("입력이 잘못됬습니다.");
            Pair<Integer, Integer> pos = (Pair<Integer, Integer>) detail;
            // 건설 작업 수행
            player.buildField(pos.first, pos.second, this.fieldType);
        } catch (Exception ex) {
            throw new RuntimeException("입력이 잘못됬습니다.");
        }
    }
}
