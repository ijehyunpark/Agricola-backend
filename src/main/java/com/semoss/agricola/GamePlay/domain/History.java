package com.semoss.agricola.GamePlay.domain;

import com.semoss.agricola.GamePlay.domain.action.ActionType;
import com.semoss.agricola.GamePlay.domain.action.EventName;
import com.semoss.agricola.GamePlay.domain.resource.ResourceStruct;
import com.semoss.agricola.util.Pair;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class History {
    private EventName eventName;
    private final List<Pair<ActionType, Integer>> actionTypesAndTimes = new ArrayList<>();
    private final List<ResourceStruct> changes = new ArrayList<>();

    @Builder
    public History(EventName eventName){
        this.eventName = eventName;
    }
    public void writeActionType(ActionType actionType, Integer actionTime) {
        this.actionTypesAndTimes.add(new Pair<>(actionType, actionTime));
    }

    public void writeResourceChange(ResourceStruct resource){
        changes.add(resource);
    }

    /**
     * add resources to storage
     * @param resources List of a pair of resource and amount of resource
     */
    public void writeResourceChange(List<ResourceStruct> resources) {
        for (ResourceStruct resource : resources)
            writeResourceChange(resource);
    }

}
