package com.semoss.agricola.GamePlay.domain.action.component;

import com.semoss.agricola.GamePlay.domain.History;
import com.semoss.agricola.GamePlay.domain.player.Player;
import lombok.Builder;
import lombok.Getter;

/**
 * increase family action
 */
@Getter
public class IncreaseFamilyAction implements SimpleAction {
    private final ActionType actionType = ActionType.ADOPT;
    private final boolean precondition;

    @Builder
    public IncreaseFamilyAction(boolean precondition) {
        this.precondition = precondition;
    }
    /**
     * increase family
     *
     * @param player player who increase family
     */
    @Override
    public void runAction(Player player, History history){
        if (!precondition || player.existEmptyRoom()){
            player.addChild();
        } else {
            throw new RuntimeException("가족 늘리기 조건 불만족");
        }
    }
}
