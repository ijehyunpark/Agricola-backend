package com.semoss.agricola.GamePlay.domain.action;

import com.semoss.agricola.GamePlay.domain.player.FieldType;
import com.semoss.agricola.GamePlay.domain.player.Player;
import com.semoss.agricola.GamePlay.domain.resource.ResourceType;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Deprecated
public class BuildAction implements SimpleAction{
    /** 두종류의 자원 셋에 중복되는 자원 타입이 없다는 가정으로 작성되었음 */
    /** Resources added by the number of actions */
    private List<Map.Entry<ResourceType,Integer>> resourceSet;
    /** Resources used only once per action */
    private List<Map.Entry<ResourceType,Integer>> resourceUsedOnce;
    private FieldType fieldType;
    @Getter
    private ActionType actionType = ActionType.BUILD;

    public BuildAction(FieldType fieldType, List<Map.Entry<ResourceType,Integer>> resourceSet){
        this.resourceSet = resourceSet;
        this.resourceUsedOnce = new ArrayList<>();
    }

    public BuildAction(FieldType fieldType, List<Map.Entry<ResourceType,Integer>> resourceSet, List<Map.Entry<ResourceType,Integer>> resourceUsedOnce){
        this.resourceSet = resourceSet;
        this.resourceUsedOnce = resourceUsedOnce;
    }

    /**
     * Check if the player satisfies the precondition
     * @param player player who check precondition
     * @return if the precondition is satisfied, return true.
     */
    public boolean checkPrecondition(Player player) {
        for (Map.Entry<ResourceType,Integer> resourceSet : resourceSet){
            if (player.getResource(resourceSet.getKey()) < resourceSet.getValue())
                return false;
        }
        for (Map.Entry<ResourceType,Integer> resourceSet : resourceUsedOnce){
            if (player.getResource(resourceSet.getKey()) < resourceSet.getValue())
                return false;
        }
        return true;
    }

    @Override
    public void runAction(Player player) {
    }

    public boolean runAction(Player player, int actionNum, int[][] pos) {
        if (pos.length != actionNum)
            return false;
        for (Map.Entry<ResourceType,Integer> resourceSet : resourceSet){
            if (player.getResource(resourceSet.getKey()) < (resourceSet.getValue() * actionNum))
                return false;
        }
        for (Map.Entry<ResourceType,Integer> resourceSet : resourceUsedOnce){
            if (player.getResource(resourceSet.getKey()) < resourceSet.getValue())
                return false;
        }

        int i = 0;
        for (int[] cls : pos){
            int j = 0;
            for(int instance : cls){
                player.buildField(i, j++, fieldType);
            }
            i++;
        }

        for (Map.Entry<ResourceType,Integer> resourceSet : resourceSet){
            player.useResource(resourceSet.getKey(),resourceSet.getValue() * actionNum);
        }
        for (Map.Entry<ResourceType,Integer> resourceSet : resourceUsedOnce){
            player.useResource(resourceSet.getKey(),resourceSet.getValue());
        }
        return true;
    }
}
