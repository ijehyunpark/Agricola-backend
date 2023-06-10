package com.semoss.agricola.GamePlay.domain.action.component;

import com.semoss.agricola.GamePlay.domain.History;
import com.semoss.agricola.GamePlay.domain.player.Player;
import lombok.Builder;
import lombok.Getter;

/**
 * 선공권 가져오기
 */
@Getter
public class GetStartingPositionAction implements Action {
    private final ActionType actionType = ActionType.STARTING;

    @Builder
    public GetStartingPositionAction() {
    }

    /**
     * 선공권을 가져옵니다.
     * @param player 행동을 수행한 플레이어
     */
    public void runAction(Player player, Player startingPlayer, History history) {
        startingPlayer.disableStartingToken();
        player.setStartingTokenByTrue();
    }
}
