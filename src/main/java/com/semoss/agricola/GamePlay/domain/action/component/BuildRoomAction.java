package com.semoss.agricola.GamePlay.domain.action.component;

import com.semoss.agricola.GamePlay.domain.player.Player;
import com.semoss.agricola.GamePlay.domain.player.RoomType;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;

/**
 * 집 건설 액션
 * 1개의 빈 필드에 새로운 건축물을 건설한다.
 * e.g 집 건설
 */
@Getter
public class BuildRoomAction implements Action {
    private final ActionType actionType = ActionType.BUILD_ROOM;
    private final Map<RoomType, BuildSimpleAction> buildActions;

    @Builder
    public BuildRoomAction(Map<RoomType, BuildSimpleAction> buildActions) {
        this.buildActions = buildActions;
    }

    /**
     * 플레이어의 필드에 집을 추가한다.
     * @param player 건설 작업을 수행할 플레이어
     */
    public void runAction(Player player, int y, int x) {
        buildActions.get(player.getRoomType()).runAction(player, y, x);
    }

}
