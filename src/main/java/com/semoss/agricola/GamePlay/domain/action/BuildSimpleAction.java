package com.semoss.agricola.GamePlay.domain.action;

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
public class BuildSimpleAction implements Action {
    @Getter
    private final ActionType actionType = ActionType.BUILD;
    private final FieldType fieldType;
    private final List<ResourceStruct> requirements;
    @Getter
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
    @Override
    public boolean checkPrecondition(Player player) {
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
     * 사용하지 않음
     * @return
     */
    @Override
    public boolean runAction() {
        return false;
    }

    /**
     * 사용하지 않음 TODO runAction 인터페이스 사양 변경
     * @param player
     * @return
     */
    @Override
    public boolean runAction(Player player) {
        throw new RuntimeException("건설 작업은 해당 함수 사용 안합니다.");
    }

    /**
     * 플레이어의 필드에 새로운 건물을 추가한다.
     * @param player 건설 작업을 수행할 플레이어
     * @return ?
     */
    public boolean runAction(Player player, int y, int x) {
        if(!checkPrecondition(player))
            throw new RuntimeException("건설을 수행할 자원이 부족합니다.");

        // 건설 작업 수행
        player.buildField(y, x, this.fieldType);
        return true;
    }
}
