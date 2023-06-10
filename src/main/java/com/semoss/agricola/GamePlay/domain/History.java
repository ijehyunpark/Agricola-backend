package com.semoss.agricola.GamePlay.domain;

import com.semoss.agricola.GamePlay.domain.action.component.ActionType;
import com.semoss.agricola.GamePlay.domain.action.implement.ActionName;
import com.semoss.agricola.GamePlay.domain.resource.ResourceStructInterface;
import com.semoss.agricola.util.Pair;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class History {
    private ActionName eventName;
    private final List<Pair<ActionType, Integer>> actionTypesAndTimes = new ArrayList<>();
    private final List<ResourceStructInterface> changes = new ArrayList<>();

    @Builder
    public History(ActionName eventName){
        this.eventName = eventName;
    }
    public void writeActionType(ActionType actionType, Integer actionTime) {
        this.actionTypesAndTimes.add(new Pair<>(actionType, actionTime));
    }

    public void writeResourceChange(ResourceStructInterface resource){
        changes.add(resource);
    }

    /**
     * add resources to storage
     * @param resources List of a pair of resource and amount of resource
     */
    public void writeResourceChange(List<ResourceStructInterface> resources) {
        for (ResourceStructInterface resource : resources)
            writeResourceChange(resource);
    }

}
