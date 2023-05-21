package com.semoss.agricola.GamePlay.domain.action;

import com.semoss.agricola.GamePlay.domain.player.Player;
import lombok.Builder;
import lombok.Getter;

/**
 * TODO: 씨 뿌리기 액션
 */

@Getter
public class CultivationAction implements MultiInputAction {
    private final ActionType actionType = ActionType.CULTIVATION;

    @Builder
    public CultivationAction() {
    }

    /**
     * TODO: 씨앗 심기 전 빈 밭인지 검증
     * @param player 씨를 뿌릴 플레이어
     * @param detail 밭의 y, x 좌표
     * @return
     */
    public boolean checkPrecondition(Player player, Object detail) {
        return false;
    }

    /**
     * TODO: 플레이어 밭에 씨를 뿌립니다.
     * @param player 씨를 뿌릴 플레이어
     * @param detail 밭의 y, x 좌표
     * @return
     */
    public void runAction(Player player, Object detail) {
        throw new RuntimeException("미구현");
    }
}
