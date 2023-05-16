package com.semoss.agricola.GamePlay.domain.player;

import com.semoss.agricola.GamePlay.domain.resource.ResourceStruct;
import com.semoss.agricola.GamePlay.domain.resource.Seed;
import lombok.Getter;

import java.util.Optional;

/**
 * 농지
 */
@Getter
public class Farm implements Field {
    private Seed seed;

    public Farm() {
        seed = Seed.seedBuilder()
                .resource(null)
                .count(0)
                .build();
    }

    /**
     * 밭에 심어진 식물을 수확한다.
     * @return 수확한 결과물
     */
    protected Optional<ResourceStruct> harvest() {
        return seed.harvest();
    }
}
