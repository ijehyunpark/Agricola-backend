package com.semoss.agricola.action;

import com.semoss.agricola.mainflow.Player;
import com.semoss.agricola.mainflow.ResourceType;

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
