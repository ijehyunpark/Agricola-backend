package com.semoss.agricola.GamePlay.domain.resource;

import lombok.Builder;

public class Seed extends ResourceStruct {
    @Builder(builderMethodName = "seedBuilder")
    Seed(ResourceType resource, int count) {
        super(resource, count);
    }

    /**
     * 수확 시간
     */
    public ResourceStruct harvest() {
        // 수확 불가능시 NULL 반환
        if(super.getResource() == null || super.getCount() == 0)
            return null;

        // 수확할 개수 측정
        int harvestCount = 1;

        // 수확 반영
        super.setCount(super.getCount() - harvestCount);

        // 수확 반환
        return ResourceStruct.builder()
                .resource(super.getResource())
                .count(harvestCount)
                .build();
    }
}
