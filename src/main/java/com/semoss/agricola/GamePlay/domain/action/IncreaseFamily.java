package com.semoss.agricola.GamePlay.domain.action;

import com.semoss.agricola.GamePlay.domain.Player;
import com.semoss.agricola.GamePlay.domain.ResourceType;

public class IncreaseFamily extends BasicAction{
    private boolean precondition;
    /**
     * increase family action
     * @param rt  resource type to add
     * @param num amount of resource
     */
    public IncreaseFamily(ResourceType rt, int num, boolean precondition) {
        super(rt, num);
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
        if (!precondition || player.familyPrecondition()){
            super.runAction(player);
            return true;
        }
        return false;
    }
}
