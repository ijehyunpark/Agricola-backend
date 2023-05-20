package com.semoss.agricola.GamePlay.domain;

import com.semoss.agricola.GamePlay.domain.resource.ResourceStruct;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class History {
    private Long eventId;
    private final List<ResourceStruct> changes;

    @Builder
    public History(Long eventId){
        this.eventId = eventId;
        changes = new ArrayList<>();
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
