package com.semoss.agricola.GamePlay.domain.action;

import com.semoss.agricola.GamePlay.domain.player.FieldType;
import com.semoss.agricola.GamePlay.domain.player.Player;
import com.semoss.agricola.GamePlay.domain.resource.ResourceStruct;
import com.semoss.agricola.GamePlay.exception.IllgalRequestException;
import com.semoss.agricola.GamePlay.exception.ResourceLackException;
import com.semoss.agricola.util.Pair;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

/**
 * 단순 필드 건설 액션
 * 1개의 빈 필드에 새로운 건축물을 건설한다.
 * e.g 밭 일구기, 마구간 건설 등
 */
@Getter
public class BuildSimpleAction implements Action {
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
    protected boolean checkPrecondition(Player player, int count) {
        // 플레이어가 요구 자원을 가지고 있는지 반환
        for (ResourceStruct requirement : requirements){
            if(!(player.getResource(requirement.getResource()) >= requirement.getCount() * count))
                return false;
        }
        return true;
    }

    public void runAction(Player player, int y, int x) {
        if(buildMaxCount == 0)
            throw new IllgalRequestException("허용되지 않은 건설 작업입니다.");


        if(!checkPrecondition(player, 1))
            throw new ResourceLackException();


        // 새로운 건물 건설
        player.buildField(y, x, this.fieldType);


        // 자원 소모
        player.useResource(requirements);
    }

    /**
     * 플레이어의 필드에 새로운 건물을 추가한다.
     * @param player 건설 작업을 수행할 플레이어
     */
    public void runAction(Player player, List<Pair<Integer, Integer>> positions) {
        int buildCount = positions.size();
        if (buildMaxCount != -1 && buildCount > buildMaxCount)
            throw new IllgalRequestException("최대 건설 횟수를 초과하였습니다.");

        positions.forEach(
                position -> runAction(player, position.first(), position.second())
        );
    }

}
