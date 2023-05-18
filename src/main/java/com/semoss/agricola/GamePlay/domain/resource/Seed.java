package com.semoss.agricola.GamePlay.domain.resource;

import lombok.Builder;

import java.util.Optional;

/**
 * 밭에 심어진 식물
 */
public class Seed extends ResourceStruct {
    @Builder(builderMethodName = "seedBuilder")
    Seed(ResourceType resource, int count) {
        super(resource, count);
    }

    /**
     * 심어진 식물을 수확한다.
     */
    public Optional<ResourceStruct> harvest() {
        // 수확 불가능시 NULL 반환
        if(super.getResource() == null || super.getCount() == 0)
            return Optional.empty();

        // 수확할 개수 측정
        int harvestCount = 1;

        // 수확 반영
        super.setCount(super.getCount() - harvestCount);

        // 수확 반환
        return Optional.of(ResourceStruct.builder()
                .resource(super.getResource())
                .count(harvestCount)
                .build());
    }
}
