package com.semoss.agricola.GamePlay.domain.action;

import com.semoss.agricola.GamePlay.domain.player.Player;
import lombok.Builder;
import lombok.Getter;

/**
 * increase family action
 */
@Getter
public class IncreaseFamily implements SimpleAction {
    private final ActionType actionType = ActionType.ADOPT;
    private final boolean precondition;

    @Builder
    public IncreaseFamily(boolean precondition) {
        this.precondition = precondition;
    }

    /**
     * Check if the player satisfies the precondition
     * @return if the precondition is satisfied, return true.
     */
    @Override
    public boolean checkPrecondition(Player player) {
        return (!precondition || player.familyPrecondition());
    }

    /**
     * increase family
     * @param player player who increase family
     * @return false if there is a precondition and the precondition is not satisfied.
     */
    @Override
    public boolean runAction(Player player){
        if (checkPrecondition(player)){
            player.addChild();
            return true;
        }
        return false;
    }
}
