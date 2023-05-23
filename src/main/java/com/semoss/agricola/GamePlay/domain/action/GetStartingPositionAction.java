package com.semoss.agricola.GamePlay.domain.action;

import com.semoss.agricola.GamePlay.domain.History;
import com.semoss.agricola.GamePlay.domain.player.Player;
import lombok.Builder;
import lombok.Getter;

/**
 * 선공권 가져오기
 */
@Getter
public class GetStartingPositionAction implements SimpleAction {
    private final ActionType actionType = ActionType.STARTING;

    @Builder
    public GetStartingPositionAction() {
    }

    /**
     * 선공권을 가져옵니다.
     * @param player 행동을 수행한 플레이어
     */
    @Override
    public void runAction(Player player, History history) {
        player.setStartingTokenByTrue();
    }
}
